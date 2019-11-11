package pl.mwiski.dieticianoffice.mapper;

import org.springframework.stereotype.Component;
import pl.mwiski.dieticianoffice.dto.AddressDto;
import pl.mwiski.dieticianoffice.entity.Address;

@Component
public class AddressMapper {

    public AddressDto toAddressDto(final Address address) {
        if (address == null) return null;
        return new AddressDto(
                address.getId(),
                address.getCity(),
                address.getPostalCode(),
                address.getStreet(),
                address.getBuildingNumber(),
                address.getApartmentNumber());
    }

    public Address toAddress(final AddressDto addressDto) {
        if (addressDto == null) return null;
        return new Address(
                addressDto.getCity(),
                addressDto.getPostalCode(),
                addressDto.getStreet(),
                addressDto.getBuildingNumber(),
                addressDto.getApartmentNumber());
    }
}
