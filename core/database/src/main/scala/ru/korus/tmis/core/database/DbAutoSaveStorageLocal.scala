package ru.korus.tmis.core.database

import javax.ejb.Local
import java.util.Date
import ru.korus.tmis.core.data.{BooleanContainer, AutoSaveOutputDataContainer}

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 4/21/14
 * Time: 8:12 PM
 */
@Local
trait DbAutoSaveStorageLocal {

  /**
   * Сохранение записи в хранилище, если запись с таким идентификатором и
   * пользователем уже существует - она будет перезаписана
   * @param id Идентификатор записи
   * @param userId Идентификатор пользователя
   * @param text Текст записи
   */
  def save(id: String, userId: Int, text: String)

  /**
   * Получение записи из хранилища
   * @param id Идентификатор записи
   * @param userId Идентификатор пользователя
   * @return Контейнер, несущий информацию о записи
   */
  def load(id: String, userId: Int): AutoSaveOutputDataContainer

  /**
   * Удаление записи из хранилища
   * @param id Идентификатор записи
   * @param userId Идентификатор пользователя
   */
  def delete(id: String, userId: Int)

  /**
   * Удаление из хранилища устаревших записей
   * @param date Дата, ранее которой все записи будут удалены
   */
  def clean(date: Date)

}
