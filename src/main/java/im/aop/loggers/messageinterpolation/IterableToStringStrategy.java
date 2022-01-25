package im.aop.loggers.messageinterpolation;

import java.util.Iterator;
import java.util.Objects;

/**
 * {@link ToStringStrategy} implementation for {@link Iterable}, using {@link
 * ObjectToStringStrategy} to return a String representation of the element. Encloses element within
 * square brackets "[]".
 *
 * @author Andy Lian
 */
public class IterableToStringStrategy implements ToStringStrategy {

  private final ObjectToStringStrategy objectToStringStrategy;

  public IterableToStringStrategy(final ObjectToStringStrategy objectToStringStrategy) {
    this.objectToStringStrategy = Objects.requireNonNull(objectToStringStrategy);
  }

  @Override
  public boolean supports(Class<?> type) {
    return Iterable.class.isAssignableFrom(type);
  }

  @Override
  public String toString(Object object) {
    return toString((Iterable<?>) object);
  }

  private String toString(final Iterable<?> iterable) {
    final Iterator<?> iterator = iterable.iterator();
    if (iterator.hasNext() == false) {
      return "[]";
    }

    final StringBuilder builder = new StringBuilder("[");
    builder.append(toStringOrNullString(iterator.next()));
    while (iterator.hasNext()) {
      builder.append(", ");
      builder.append(toStringOrNullString(iterator.next()));
    }

    return builder.append("]").toString();
  }

  private Object toStringOrNullString(Object element) {
    return element == null ? "null" : objectToStringStrategy.toString(element);
  }
}
