package unlp.labo.spg.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(indices = {@Index(value = "userName", unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    private long uid;

    @NonNull
    private String userName;

    @NonNull
    private String password;

    public User(@NonNull String userName, @NonNull String password) {
        this.userName = userName;
        this.password = password;

    }

    public long getUid() { return uid; }

    public void setUid(long uid) { this.uid = uid; }

    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
