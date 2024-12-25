package m7011e.the_homeric_odyssey.core.services;

import java.util.List;
import java.util.UUID;
import m7011e.the_homeric_odyssey.coreorm.orm.AbstractDbObject;

public interface IPersistenceService<DB extends AbstractDbObject, DOMAIN> {
  DOMAIN create(DOMAIN entity);

  DOMAIN update(DOMAIN entity, UUID id, Long version);

  void delete(UUID id, Long version);

  DOMAIN get(UUID id);

  List<DOMAIN> getAll();

  DOMAIN mapToDomain(DB entity);

  DB mapToDB(DOMAIN domain);
}
