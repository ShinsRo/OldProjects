package org.sejonghacker.botum;

import java.util.List;

public interface RawDAO<E> {
	Object insert(E params);
	E select(E params);
	Object delete(E params);
	Object update(E params);
	List<E> getAllList(E params);
	Integer getCount();

}