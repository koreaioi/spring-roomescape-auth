package roomescape.common.auth.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import roomescape.common.auth.annotation.CurrentManagedStore;
import roomescape.common.auth.exception.AuthException;
import roomescape.management.exception.ManagementException;
import roomescape.store.domain.ManagedStore;
import roomescape.management.domain.Management;
import roomescape.management.repository.ManagementRepository;

import java.util.List;

import static roomescape.common.auth.exception.AuthExceptionInformation.UN_AUTHORIZED;
import static roomescape.management.exception.ManagementErrorInformation.NO_MANAGEMENT_STORE;

@Component
public class ManagedStoreArgumentResolver implements HandlerMethodArgumentResolver {

    private final ManagementRepository managementRepository;

    public ManagedStoreArgumentResolver(ManagementRepository managementRepository) {
        this.managementRepository = managementRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentManagedStore.class);
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        Long managerId = (Long) request.getAttribute("memberId");
        if (managerId == null) {
            throw new AuthException(UN_AUTHORIZED);
        }

        List<Management> managements = managementRepository.findAllByManagerId(managerId);
        if (managements.isEmpty()) {
            throw new ManagementException(NO_MANAGEMENT_STORE);
        }

        return ManagedStore.from(managements);
    }

}
