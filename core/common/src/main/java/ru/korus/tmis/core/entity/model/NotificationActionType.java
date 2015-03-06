package ru.korus.tmis.core.entity.model;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        13.08.14, 17:41 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "NotificationActionType")
@NamedQueries(
        {
                @NamedQuery(name = "NotificationActionType.findByActionType",
                        query = "SELECT n FROM NotificationActionType n WHERE n.actionType.id = :actionTypeId"),
                @NamedQuery(name = "NotificationActionType.findByUrl",
                        query = "SELECT n FROM NotificationActionType n WHERE n.baseUrl LIKE :baseUrl")
        }
)
public class NotificationActionType implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String baseUrl;
    private ActionType actionType;

    public NotificationActionType() {
    }


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getBaseUrl() {
        return this.baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @ManyToOne
    public ActionType getActionType() {
        return this.actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

}