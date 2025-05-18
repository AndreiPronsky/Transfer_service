package org.pronsky.transfer_service.service.mapper;

import org.mapstruct.Mapper;
import org.pronsky.transfer_service.data.entity.PhoneData;
import org.pronsky.transfer_service.service.dto.PhoneDataDto;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    PhoneDataDto phoneDataEntityToPhoneDataDto(PhoneData phoneData);

}
