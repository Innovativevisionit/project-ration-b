package com.sql.authentication.serviceimplementation.process;

import com.sql.authentication.dto.PurchaseDto;
import com.sql.authentication.exception.NotFoundException;
import com.sql.authentication.model.*;
import com.sql.authentication.payload.response.UserListRes;
import com.sql.authentication.repository.LocationProductRepository;
import com.sql.authentication.repository.ProductRepository;
import com.sql.authentication.repository.UserProdPurchaseRepository;
import com.sql.authentication.repository.UserRepository;
import com.sql.authentication.service.process.ProductPurchaseService;

import com.sql.authentication.serviceimplementation.auth.UserDetailsImpl;
import com.sql.authentication.utils.AuthDetails;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.squareup.okhttp.*;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
@Service
public class ProductPurchaseServiceImpl implements ProductPurchaseService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private LocationProductRepository locationProductRepository;
    @Autowired
    private UserProdPurchaseRepository userProdPurchaseRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthDetails authDetails;

    public PurchaseDto store(PurchaseDto dto) throws IOException {
        UserProdPurchase userProdPurchase=new UserProdPurchase();
        User user= userRepository.findBySmartId(dto.getSmartId()).orElseThrow(()->new NotFoundException(dto.getSmartId()+ "is not found"));
        Product product=productRepository.findByName(dto.getProduct()).orElseThrow(()->new NotFoundException(dto.getProduct()+"is not found"));
        LocationProduct locationProduct=locationProductRepository.findByLocationAndProduct(user.getLocation(),product);
        userProdPurchase.setUser(user);
        userProdPurchase.setProduct(product);
        userProdPurchase.setAmount(dto.getAmount());
        userProdPurchase.setKg(dto.getKg());
        LocalDate date=LocalDate.now();
        userProdPurchase.setPurchasedDate(date);
        YearMonth yearMonth=YearMonth.of(date.getYear(), date.getMonthValue());
        userProdPurchase.setMonth(yearMonth);
        locationProduct.setStockKg(locationProduct.getStockKg().subtract(dto.getKg()));
        locationProduct.setDeliveredKg(locationProduct.getStockKg().add(dto.getKg()));
        userProdPurchaseRepository.save(userProdPurchase);
        if(user.getContactNo()!=null) {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            String data="Dear User,"+product+" was bought successfully ";
            String jsonBody = "{\n" +
                    "  \"messages\": [\n" +
                    "    {\n" +
                    "      \"body\": \""+data+"\",\n" +
                    "      \"to\": \"" + user.getContactNo() + "\",\n" + // Insert the contactNo dynamically
                    "      \"from\": \"8248108074\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";
            com.squareup.okhttp.RequestBody body = RequestBody.create(mediaType,jsonBody);
            Request request = new Request.Builder()
                    .url("https://rest.clicksend.com/v3/sms/send")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Basic YXl5YW5hci5hckBoZXBsLmNvbToyMkQ2RENEMS00QzE5LTAyMzItRDg1Qy1GOTBGNzE0QTJDNTM=")
                    .build();
            Response response = client.newCall(request).execute();
            try {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected response code: " + response);
                }
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            } finally {
                response.body().close();
            }
        }
        return dto;
    }

    @Override
    public List<PurchaseDto> list(String email) {
        List<UserProdPurchase> result = userProdPurchaseRepository.findAll();
        return result
                .stream()
               .map(list->{
                    return modelMapper.map(list, PurchaseDto.class);
                }).toList();
    }

    @Override
    public List<PurchaseDto> purchaseList(String email) {

        User userList=userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User is not found."));
        List<UserProdPurchase> result = userProdPurchaseRepository.findByUser(userList);
        return result
                .stream()
               .map(list->{
                    return modelMapper.map(list, PurchaseDto.class);
                }).toList();
    }

    public List<PurchaseDto> employeeUserPurchaseList(String email){
        Optional<User> user=userRepository.findByEmail(email);
        List<UserProdPurchase> userProdPurchases=userProdPurchaseRepository.findByUser_location(user.get().getLocation());
        return userProdPurchases.stream().map(data->{
            PurchaseDto dto=modelMapper.map(data,PurchaseDto.class);
            dto.setSmartId(data.getUser().getSmartId());
            return dto;
        }).toList();

    }
}
