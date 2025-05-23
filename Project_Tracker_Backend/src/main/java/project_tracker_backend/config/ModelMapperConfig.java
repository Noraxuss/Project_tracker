package project_tracker_backend.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project_tracker_backend.domain.Status;
import project_tracker_backend.domain.StatusPurpose;
import project_tracker_backend.dto.incoming.StatusCommand;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);

        modelMapper.createTypeMap(StatusCommand.class, Status.class)
                .addMappings(mapper ->
                        mapper.using(context ->
                                StatusPurpose.valueOf(context.getSource().toString()))
                        .map(StatusCommand::getStatusPurpose, Status::setStatusPurpose));
        return modelMapper;
    }
}
