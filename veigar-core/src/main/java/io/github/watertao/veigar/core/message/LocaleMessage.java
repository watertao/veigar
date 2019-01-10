package io.github.watertao.veigar.core.message;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;


@Component
public class LocaleMessage {

  public String m(String key, Object... variables) {
    Locale locale = LocaleContextHolder.getLocale();
    ResourceBundle bundle = ResourceBundle.getBundle("message.message", locale);
    return MessageFormat.format(bundle.getString(key), variables);
  }

  public String bm(String bundleName, String key, Object... variables) {
    Locale locale = LocaleContextHolder.getLocale();
    ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
    return MessageFormat.format(bundle.getString(key), variables);
  }

}
