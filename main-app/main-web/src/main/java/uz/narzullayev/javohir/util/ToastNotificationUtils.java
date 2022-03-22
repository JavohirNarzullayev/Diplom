package uz.narzullayev.javohir.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.narzullayev.javohir.constant.ToastNotificationType;
import uz.narzullayev.javohir.dto.ToastNotification;

import java.util.LinkedList;
import java.util.List;


@UtilityClass
public class ToastNotificationUtils {


    private static final String modelParameterName = "toaster_notifications";

    @SuppressWarnings("unchecked")
    public static void addMessage(RedirectAttributes model, ToastNotificationType type, String message) {
        List<ToastNotification> toastrNotificationList;
        if (!model.containsAttribute(modelParameterName)) {
            toastrNotificationList = new LinkedList<>();
        } else {
            toastrNotificationList = (List<ToastNotification>) model.asMap().get(modelParameterName);
        }
        toastrNotificationList.add(new ToastNotification(type, message));
        model.addFlashAttribute(modelParameterName, toastrNotificationList);
    }

    public static void addSuccess(RedirectAttributes model, String message) {
        addMessage(model, ToastNotificationType.success, message);
    }

    public static void addInfo(RedirectAttributes model, String message) {
        addMessage(model, ToastNotificationType.info, message);
    }

    public static void addWarning(RedirectAttributes model, String message) {
        addMessage(model, ToastNotificationType.warning, message);
    }

    public static void addError(RedirectAttributes model, String message) {
        addMessage(model, ToastNotificationType.error, message);
    }
}