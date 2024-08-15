package com.blez.trip.service;


import com.blez.trip.model.EmailDetails;
import com.blez.trip.model.TripModel;
import com.blez.trip.model.CarpoolingRoute;
import com.blez.trip.repository.TripRepo;
import com.blez.trip.utils.ResultState;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String sender;

    Logger logger = Logger.getLogger(TripServiceImpl.class.getName());

    @Autowired
    private TemplateEngine templateEngine; // Inject the Thymeleaf template engine


    @Autowired
    private TripRepo tripRepo;

    @Override
    public ResultState<String> createTrip(TripModel tripModel) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            TripModel savedState = tripRepo.save(tripModel);
            String url = "http://localhost:8765/ride-service/ride/"+tripModel.getR_userId()+"/"+savedState.getSeatsOccupied();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<Integer> entity = new HttpEntity<>(null, headers);

            ResponseEntity<Void> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    entity,
                    Void.class
            );
            if (response.getStatusCode() != HttpStatus.OK) {
                return new ResultState.Error<>("Error while creating trip.");
            }

            if (savedState == null) {
                return new ResultState.Error<>("Error while creating trip.");
            } else {

                EmailDetails c_user = new EmailDetails(tripModel.getC_email(), "", "Trip Details -eBill", "");
                mimeMessageHelper.setFrom(sender);
                String[] emails = {tripModel.getC_email(),tripModel.getR_email()};
                mimeMessageHelper.setTo(emails);
                mimeMessageHelper.setSubject(c_user.getSubject());
                String emailContent = buildEmailContent(tripModel);
                mimeMessageHelper.setText(emailContent, true);
                javaMailSender.send(mimeMessage);
                return new ResultState.Success<>("Trip has been created successfully.");
            }

        } catch (Exception e) {
            logger.severe(e.getMessage());
            return new ResultState.Error<>("Error while creating trip.");
        }
    }


    private String buildEmailContent(TripModel tripModel) {
        Context context = new Context();
        context.setVariable("tripId", tripModel.getTrip_id());
        context.setVariable("cName", tripModel.getC_name());
        context.setVariable("cEmail", tripModel.getC_email());
        context.setVariable("rideSource", tripModel.getRideSource());
        context.setVariable("rideDestination", tripModel.getRideDestination());
        context.setVariable("rideDate", tripModel.getRideDate());
        context.setVariable("fare", tripModel.getFare());
        context.setVariable("carName", tripModel.getCarName());
        context.setVariable("carNumber", tripModel.getCarNumber());
        context.setVariable("rEmail", tripModel.getR_email());
        context.setVariable("rUserId", tripModel.getR_userId());

        // Use Thymeleaf template engine to process the template
        return templateEngine.process("trip-email", context);
    }


    @Override
    public ResultState<String> deleteTrip(int id) {
        try {
           tripRepo.deleteById(id);
            return new ResultState.Success<>("Trip has been deleted successfully.");
        }catch (Exception e) {
            logger.severe(e.getMessage());
            return new ResultState.Error<>("Error while getting trips.");
        }
    }

    @Override
    public ResultState<TripModel> getTripById(int id) {
        try {
            Optional<TripModel> trips = tripRepo.findById(id);
            if (trips.isPresent()) {
                return new ResultState.Success<>(trips.get());
            }else {
                return new ResultState.Error<>("No Trip found with id " + id);
            }
        }catch (Exception e) {
            logger.severe(e.getMessage());
            return new ResultState.Error<>("Error while getting trips.");
        }
    }

    @Override
    public ResultState<List<TripModel>> getTripsByCId(String cid) {
       try {
           List<TripModel> trips = tripRepo.findByCUserIdNative(cid);
          return new ResultState.Success<>(trips);
       }catch (Exception e) {
           logger.severe(e.getMessage());
           return new ResultState.Error<>("Error while getting trips.");
       }
    }

    @Override
    public ResultState<List<TripModel>> getTripByRid(String rId) {
        try {
            List<TripModel> trps = tripRepo.findByRUserIdNative(rId);
            return new ResultState.Success<>(trps);

        }catch (Exception e) {
            logger.severe(e.getMessage());
            return new ResultState.Error<>("Error while getting trips.");
        }
    }

    @Override
    public ResultState<List<Object>> getAllCities() {
        try {
            List<Object> cities = tripRepo.getAllCities();
            if (cities.size() <= 0) {
                return new ResultState.Error<>("No cities found.");
            }
            return new ResultState.Success<>(cities);
        }catch (Exception e) {
            logger.severe(e.getMessage());
            return new ResultState.Error<>("Error while getting cities.");
        }
    }
}
