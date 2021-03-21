package phase3.core.service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import phase3.core.entities.Coupon;
import phase3.core.repositories.CouponRepository;



@Component
public class CouponExpirationDailyJob implements Runnable {

	@Autowired
	private CouponRepository couponRep;
	private boolean quit = false;
	private final LocalDate localDate = LocalDate.now();
	private Thread owner;

	public CouponExpirationDailyJob() {
	}
	
	

	public void setOwner(Thread owner) {
		this.owner = owner;
	}
	
	



	@Override
	public void run() {

		while (!quit) {

			try {

				List<Coupon> list = couponRep.findAll();

				for (int i = 0; i < list.size(); i++) {

					if (list.get(i).getEndDate().isBefore(localDate)) {
						couponRep.delete(list.get(i));
					}

				}
				// quit = true;
				Thread.sleep(TimeUnit.HOURS.toMillis(24));

			}

			catch (InterruptedException e) {
				break;
			}
		}

	}
	
	@PostConstruct
	public void start() {
		
		System.out.println(">>> starting daily job thread");
		Thread t1 = new Thread(this, "JOB");
		t1.start();
		this.setOwner(t1);
		
	}

	@PreDestroy
	public void stop() {

		quit = true;
		this.owner.interrupt();
		System.out.println(this.owner.getName());
		System.out.println("stop daily job");

	}

}
