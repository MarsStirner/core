package ru.korus.tmis.tariff;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.ContractTariff;

import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 08.08.13, 19:09 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class Helper {
    public static final int C_TAR_SIZE = 18;
    private static final Logger logger = LoggerFactory.getLogger(Helper.class);

    private Helper() {
    }

    public static void storeNewTariffsToDatabase(final List<ContractTariff> tariffList) {
        for (ContractTariff currentTariffFromTfoms : tariffList) {
           List<ContractTariff> alikeTariffList = TariffServer.getSpecificBean().getAlikeTariff(currentTariffFromTfoms);
            if(alikeTariffList.isEmpty()){
                logger.debug("NEW (persist) {}", currentTariffFromTfoms.getInfo());
                TariffServer.getTariffBean().persistTariff(currentTariffFromTfoms);
            } else {
                logger.debug("SAME (not persist)");
                for(ContractTariff currentAlikeTariff : alikeTariffList){
                    if(currentAlikeTariff.getDeleted() != 0){
                        logger.debug("RESTORE deleted [{}]", currentAlikeTariff.getId());
                        currentAlikeTariff.setDeleted((short) 0);
                        TariffServer.getTariffBean().updateTariff(currentAlikeTariff);
                    }
                }
            }
        }
    }

    public static class SexAndAge {
        private Short sex;
        private String age;

        public SexAndAge(final Short sex, final String age) {
            this.sex = sex;
            this.age = age;
        }

        public Short getSex() {
            return sex;
        }

        public void setSex(final Short sex) {
            this.sex = sex;
        }

        public String getAge() {
            return age;
        }

        public void setAge(final String age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "SEX=" + sex + " AGE=" + age;
        }
    }
}
