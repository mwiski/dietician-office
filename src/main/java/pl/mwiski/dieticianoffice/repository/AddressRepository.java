package pl.mwiski.dieticianoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mwiski.dieticianoffice.entity.Address;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Transactional
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findDistinctByPostalCodeAndCityAndStreetAndBuildingNumberAndApartmentNumber(
            @NotNull String postalCode,
            @NotNull String city,
            @NotNull String street,
            @NotNull String buildingNumber,
            String apartmentNumber);
}
