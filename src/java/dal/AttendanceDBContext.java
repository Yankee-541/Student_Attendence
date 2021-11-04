
package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Attendance;

/**
 *
 * @author Tebellum
 */
public class AttendanceDBContext extends DBConnect {

    public boolean insertAttendace(ArrayList<Attendance> attendances) {
        try {
            String sql_delete = "delete from Attendance where cid = ?";
            PreparedStatement stm_delete = connection.prepareStatement(sql_delete);
            stm_delete.setString(1, attendances.get(0).getCid());
            stm_delete.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = "INSERT INTO [Attendance]\n"
                + "           ([sid]\n"
                + "           ,[cid]\n"
                + "           ,[present])\n"
                + "     VALUES(?,?,?)";
        try {
            for (Attendance a : attendances) {
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setInt(1, a.getSid());
                stm.setString(2, a.getCid());
                stm.setBoolean(3, true);
                stm.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;

    }

}
