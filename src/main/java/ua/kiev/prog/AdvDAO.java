package ua.kiev.prog;

import java.util.List;

public interface AdvDAO {
	List<Advertisement> list();
    List<Advertisement> listDeleted();
    List<Advertisement> list(String pattern);
	void add(Advertisement adv);
    void delete(long id,boolean prBascket);
    void undelete(long id);
    byte[] getPhoto(long id);
}
