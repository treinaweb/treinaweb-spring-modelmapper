package br.com.treinaweb.twprojects.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.treinaweb.twprojects.core.models.Client;
import br.com.treinaweb.twprojects.core.utils.StringUtils;
import br.com.treinaweb.twprojects.web.clients.dtos.ClientForm;

@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(ClientForm.class, Client.class)
            .addMappings(mapper -> mapper
                .using(toCleanedPhone())
                .map(ClientForm::getPhone, Client::setPhone)
        );

        modelMapper.createTypeMap(Client.class, ClientForm.class)
            .addMappings(mapper -> mapper
                .using(toFormattedPhone())
                .map(Client::getPhone, ClientForm::setPhone)
        );

        return modelMapper;
    }

    private Converter<String, String> toCleanedPhone() {
        return context -> StringUtils.cleanPhone(context.getSource());
    }

    private Converter<String, String> toFormattedPhone() {
        return context -> StringUtils.formatPhone(context.getSource());
    }
    
}
