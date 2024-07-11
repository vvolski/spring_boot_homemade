package com.colvir.spring_boot_homework.mapper;

import com.colvir.spring_boot_homework.dto.PayOrdListResponse;
import com.colvir.spring_boot_homework.dto.PayOrdRequest;
import com.colvir.spring_boot_homework.dto.PayOrdResponse;
import com.colvir.spring_boot_homework.model.PayOrd;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PayOrdMapper {

    PayOrd requestToPayOrd(PayOrdRequest request);
    PayOrdResponse payOrdToResponse(PayOrd payOrd);
    List<PayOrdResponse> payOrdListToResponseList(List<PayOrd> payOrds);
    default PayOrdListResponse payOrdListToResponse(List<PayOrd> payOrds) {
        return new PayOrdListResponse(payOrdListToResponseList(payOrds));
    }
}
