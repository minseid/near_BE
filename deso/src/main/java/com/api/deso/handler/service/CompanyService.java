package com.api.deso.handler.service;

import com.api.deso.ifs.CrudInterface;
import com.api.deso.model.entity.Company;
import com.api.deso.model.network.Header;
import com.api.deso.model.network.request.CompanyApiRequest;
import com.api.deso.model.network.response.CompanyApiResponse;
import com.api.deso.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService implements CrudInterface<CompanyApiRequest, CompanyApiResponse> {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Header<CompanyApiResponse> create(Header<CompanyApiRequest> request) {
        CompanyApiRequest companyApiRequest=request.getData();
        Company company=Company.builder()
                .name(companyApiRequest.getName())
                .phoneNumber(companyApiRequest.getPhoneNumber())
                .businessNumber(companyApiRequest.getBusinessNumber())
                .location(companyApiRequest.getLocation())
                .email(companyApiRequest.getEmail())
                .onlineBusinessNum(companyApiRequest.getOnlineBusinessNum())
                .build();
        Company newCompany = companyRepository.save(company);
        return Header.OK(reponse(newCompany));
    }



    @Override
    public Header<CompanyApiResponse> read(Long id) {
        Company company=companyRepository.findById(id).orElse(null);
        if(company==null)
            return Header.ERROR("company dosen't exist");
        else
            return Header.OK(reponse(company));
    }

    @Override
    public Header<CompanyApiResponse> update(Header<CompanyApiRequest> request) {
        // 1. 요청 데이터 가져오기
        CompanyApiRequest companyApiRequest = request.getData();
        // 2. ID로 company 찾기
        Company company=companyRepository.findById(companyApiRequest.getId()).orElse(null);
        Company company1 = Company.builder()
                .id(companyApiRequest.getId())
                .name(companyApiRequest.getName())
                .phoneNumber(companyApiRequest.getPhoneNumber())
                .businessNumber(companyApiRequest.getBusinessNumber())
                .location(companyApiRequest.getLocation())
                .email(companyApiRequest.getEmail())
                .onlineBusinessNum(companyApiRequest.getOnlineBusinessNum())
                .build();
        companyRepository.save(company1);
        return Header.OK(reponse(company1));
    }

    @Override
    public Header<CompanyApiResponse> delete(Long id) {
        companyRepository.deleteById(id);
        return Header.OK();
    }
    private CompanyApiResponse reponse(Company company) {
        CompanyApiResponse companyApiResponse = CompanyApiResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .phoneNumber(company.getPhoneNumber())
                .businessNumber(company.getBusinessNumber())
                .location(company.getLocation())
                .email(company.getEmail())
                .onlineBusinessNum(company.getOnlineBusinessNum())
                .build();
        return companyApiResponse;
    }
}
