package com.packers.movers.commons.contracts;

import com.packers.movers.commons.contracts.validation.ContractValidationException;
import com.packers.movers.commons.contracts.validation.ValidatableContract;
import com.packers.movers.commons.utils.JsonUtils;
import com.packers.movers.commons.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class JsonBodyReader implements MessageBodyReader {
    private static final Logger LOG = LoggerFactory.getLogger(JsonBodyReader.class);

    @Override
    public boolean isReadable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        boolean canRead = mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
        LOG.trace(canRead ? "Can read media type {}" : "Can not read media type {}", mediaType);

        return canRead;
    }

    @Override
    public Object readFrom(
        Class type,
        Type genericType,
        Annotation[] annotations,
        MediaType mediaType,
        MultivaluedMap httpHeaders,
        InputStream entityStream
    ) throws IOException, WebApplicationException {
        try {
            String json = StreamUtils.toString(entityStream);

            LOG.trace("Deserializing json: {}", json);
            Object object = JsonUtils.deserialize(json, type);

            if (object == null) {
                ErrorContract errorContract = new ErrorContract("Unable to deserialize json");
                Response errorResponse = Response.status(Response.Status.BAD_REQUEST).entity(errorContract.toJson()).build();
                throw new BadRequestException(errorResponse);
            }

            boolean isValidatableContract = object instanceof ValidatableContract;
            if (!isValidatableContract) {
                return object;
            }

            try {
                LOG.trace("Validating contract");
                ValidatableContract validatableContract = (ValidatableContract) object;
                validatableContract.validate();
            } catch (ContractValidationException exception) {
                ErrorContract errorContract = new ErrorContract(exception.getValidationErrors());
                Response errorResponse = Response.status(Response.Status.BAD_REQUEST).entity(errorContract.toJson()).build();
                throw new BadRequestException(errorResponse, exception);
            }

            return object;

        } catch (WebApplicationException exception) {
            throw exception;

        } catch (Exception exception) {
            LOG.error(exception.getMessage(), exception);
            throw new InternalServerErrorException(exception);
        }
    }
}