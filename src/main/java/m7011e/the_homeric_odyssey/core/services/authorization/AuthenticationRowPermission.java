package m7011e.the_homeric_odyssey.core.services.authorization;


public interface AuthenticationRowPermission<T> {

  boolean hasReadPermission(T resource);

  boolean hasWritePermission(T resource);
}
