package utf8.citicup.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用于风险跟踪过程中的提示信息实体类
 */
@Entity
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private Boolean readStatus;

    private String message;

    private String title;

    private String time;

    public Message(String username,String message, String title){
        readStatus=false;
        this.username=username;
        this.message=message;
        this.title = title;
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        this.time = dateFormat.format(date);
    }

    public Message(){}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public boolean getReadStatus() { return readStatus; }

    public void setReadStatus(boolean readStatus) { this.readStatus = readStatus; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }
}
