package FlooringMastery.dao;

public interface ClassRosterAuditDao {

    public void writeAuditEntry(String entry) throws ClassRosterPersistenceException;

}
