package com.example;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	CommandLineRunner runner (ReservationRepository rr){
		return args -> {
			Arrays.asList("Shambhu,Shah,Anil,Shah".split(","))
			.forEach(n -> rr.save(new Reservation(n)));
			
			rr.findAll().forEach(System.out::println);
			
			rr.findByReservationName("Shambhu").forEach(System.out::println);

		};	
	}
}

/*@RestController
class ReservationRestController{
	
	@RequestMapping("/reservations")
	Collection<Reservation> reservations(){
		return this.reservationRepository.findAll();
	}
	
	@Autowired
	private ReservationRepository reservationRepository;
}*/
@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation,Long>{
	Collection <Reservation> findByReservationName (@Param("rn") String rn);

}	

@Entity
class Reservation{
	@Id
	@GeneratedValue
	private long id;
	
	private String reservationName;
	
	Reservation(){
		
	}
    public Reservation(String reservationName){
		this.reservationName=reservationName;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", reservationName=" + reservationName + "]";
	}

	public Long getId(){
		return id;
	}

	public String getReservationName() {
		return reservationName;
	}

}