package phase3.core.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phase3.core.entities.Company;
import phase3.core.entities.Coupon;
import phase3.core.entities.Coupon.Category;
import phase3.core.repositories.CompanyRepository;
import phase3.core.repositories.CouponRepository;
import phase3.core.repositories.CouponsExceptions;

@Service
@Transactional
public class CompanyImpl implements CompanyService, ClientService {

	@Autowired
	private CompanyRepository companyRep;

	@Autowired
	private CouponRepository couponRep;

	private int companyId;

	LocalDate ld = LocalDate.now();

	public CompanyImpl() {

	}

	@Override
	public boolean login(String email, String password) throws CouponsExceptions {
		Company comp = companyRep.findByEmailAndPassword(email, password);
		System.out.println(comp);
	//	int compId = comp.getId();
		if (comp!=null) {
			this.companyId = comp.getId();
			System.out.println(companyId);
			return true;
		} else {
			throw new CouponsExceptions("Failed logging in");
		}

	}

	@Override
	public void addCoupon(Coupon coupon) throws CouponsExceptions {

		Company com = companyRep.findById(companyId).get();
		List<Coupon> list = com.getCoupons();

		for (int i = 0; i < list.size(); i++) {

			if ((list.get(i).getTitle().equals(coupon.getTitle()))) {
				throw new CouponsExceptions("Coupon title already exists");

			}

		}
		if (coupon.getEndDate().isBefore(coupon.getStartDate())) {
			throw new CouponsExceptions("Coupon end date is before coupon start date");
		}
		if (coupon.getEndDate().isBefore(ld)) {
			throw new CouponsExceptions("Coupon has already expired");
		}
		if (coupon.getEndDate().isBefore(ld)) {
			throw new CouponsExceptions("Coupon has already expired");
		}
		if (coupon.getAmount() <= 0) {
			throw new CouponsExceptions("Amount has to be greater than zero");
		}
		if (coupon.getCategory() == null) {
			throw new CouponsExceptions("Category can not be null");
		}
		if (coupon.getEndDate() == null) {
			throw new CouponsExceptions("End date can not be null");
		}
		if (coupon.getStartDate() == null) {
			throw new CouponsExceptions("Startdate can not be null");
		}
		if (coupon.getDescription() == null) {
			throw new CouponsExceptions("Description can not be null");
		}
		if (coupon.getTitle() == null) {
			throw new CouponsExceptions("Title can not be null");
		}
		if (coupon.getImage() == null) {
			throw new CouponsExceptions("Image can not be null");
		}
		if (coupon.getPrice() <= 0) {
			throw new CouponsExceptions("Price has to be greater than zero");
		}

		com.addCoupon(coupon);
		companyRep.save(com);
		System.out.println(companyId);
	}

	@Override
	public void updateCoupon(Coupon coupon) throws CouponsExceptions {
		Coupon cou = couponRep.findById(coupon.getId()).get();

		if (cou == null) {
			throw new CouponsExceptions("There is not coupon");
		}
		if (couponRep.findByCompany_id(coupon.getId()) != companyId) {
			throw new CouponsExceptions("you can not update this coupon, coupon belongs to other company");

		}
		if (coupon.getEndDate().isBefore(coupon.getStartDate())) {
			throw new CouponsExceptions("Can not update, coupon end date is before coupon start date");
		}
		if (coupon.getEndDate().isBefore(ld)) {
			throw new CouponsExceptions("Can not update, coupon has already expired");
		}
		if (coupon.getAmount() <= 0) {
			throw new CouponsExceptions("Can not update, amount has to be greater than zero");
		}
		if (coupon.getCategory() == null) {
			throw new CouponsExceptions("Can not update, category can not be null");
		}
		if (coupon.getEndDate() == null) {
			throw new CouponsExceptions("Can not update, end date can not be null");
		}
		if (coupon.getStartDate() == null) {
			throw new CouponsExceptions("Can not update, start date can not be null");
		}
		if (coupon.getDescription() == null) {
			throw new CouponsExceptions("Can not update, sescription can not be null");
		}
		if (coupon.getTitle() == null) {
			throw new CouponsExceptions("Can not update, title can not be null");
		}
		if (coupon.getImage() == null) {
			throw new CouponsExceptions("Can not update, image can not be null");
		}
		if (coupon.getPrice() <= 0) {
			throw new CouponsExceptions("Can not update, price has to be greater than zero");
		}

		cou.setCategory(coupon.getCategory());
		cou.setTitle(coupon.getTitle());
		cou.setDescription(coupon.getDescription());
		cou.setStartDate(coupon.getStartDate());
		cou.setEndDate(coupon.getEndDate());
		cou.setAmount(coupon.getAmount());
		cou.setPrice(coupon.getPrice());
		cou.setImage(coupon.getImage());
		couponRep.save(cou);

	}

	@Override
	public void deleteCoupon(int couponId) throws CouponsExceptions {
		Coupon cou;
		if (!couponRep.findById(couponId).isPresent()) {
			throw new CouponsExceptions("Coupon Id doesnt exists");
		} else {
			cou = couponRep.findById(couponId).get();
			couponRep.delete(cou);
		}

	}

	@Override
	public List<Coupon> getCompaniesCoupons() throws CouponsExceptions {

		System.out.println(companyId);

		Company company = companyRep.getOne(companyId);
		List<Coupon> list = company.getCoupons();
		if (list.size() == 0) {
			throw new CouponsExceptions("This comapany doesn't have coupons");
		} else {
			System.out.println(list);
			return list;
		}
	}

	@Override
	public List<Coupon> getCompaniesCoupons(Category category) throws CouponsExceptions {

		List<Coupon> list = companyRep.findById(companyId).get().getCoupons();
		List<Coupon> listCouponsNew = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {

			if ((list.get(i).getCategory().equals(category))) {

				listCouponsNew.add(list.get(i));

			}

		}
		if (!listCouponsNew.isEmpty()) {
			System.out.println(listCouponsNew);
			return listCouponsNew;

		}else {
			throw new CouponsExceptions("the company doesnt have coupons with this category " + category);
		}

	}

	@Override
	public List<Coupon> getCompaniesCoupons(double maxPrice) throws CouponsExceptions {

		List<Coupon> list = companyRep.findById(companyId).get().getCoupons();
		List<Coupon> listNew = new ArrayList<Coupon>();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getPrice() < maxPrice) {
				listNew.add(list.get(i));
			}
		}

		if (listNew.isEmpty()) {
			throw new CouponsExceptions("There are not coupons under this maxPrice");
		} else {
			return listNew;
		}

	}

	@Override
	public Company getCompanyDetails() throws CouponsExceptions {
		Company com = companyRep.findById(companyId).get();
		if (com != null) {
			return com;
		} else {
			throw new CouponsExceptions("Failed getting company details");

		}

	}

}
