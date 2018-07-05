package ua.com.juja.cmd.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Implementation of DataSet interface for data from database using Map interface
 */
public class DBDataSet implements DataSet {
    private final static Logger LOG = LogManager.getLogger();
    private final Map<String, Object> data = new LinkedHashMap<>();

    /**
     * Adds association of given name and value to the DataSet
     *
     * @param name
     * @param value
     */
    @Override
    public void put(String name, Object value) {
        data.put(name, value);
    }

    /**
     * Updates DBDataSet with newData
     *
     * @param newData DataSet to update current DBDataSet with
     */
    @Override
    public void update(DataSet newData) {
        Set<String> columns = newData.getNames();
        for (String column : columns) {
            data.put(column, newData.get(column));
        }
    }

    /**
     * Returns value to which specified name is mapped
     *
     * @param name String key
     * @return Object associated with given name
     */
    @Override
    public Object get(String name) {
        return data.get(name);
    }

    /**
     * Returns Set of String names stored in the DataSet using Map.keySet()
     *
     * @return Set of String names stored in the DataSet
     */
    @Override
    public Set<String> getNames() {
        return data.keySet();
    }

    /**
     * Returns list of values stored in DataSet using Map.values
     *
     * @return List<Object>
     */
    @Override
    public List<Object> getValues() {
        return new ArrayList<>(data.values());
    }


    @Override
    public String toString() {
        return Arrays.deepToString(data.keySet().toArray());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBDataSet dbDataSet = (DBDataSet) o;
        return Objects.equals(data, dbDataSet.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
