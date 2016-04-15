package ru.korus.tmis.util;

public interface CompileTimeConfigManager {
    public static interface Laboratory {
        public final String Namespace = "http://www.korusconsulting.ru";
        public final String ServiceName = "IntegrationCorus";
    }

    public static interface Laboratory2 {
        public final String Namespace = "ru.novolabs.Integration.FTMIS";
        public final String ServiceName = "QueryAnalysisService";
    }

    public static interface LaboratoryClient {
        public final String Namespace = "http://korus.ru/tmis/client-laboratory";
        public final String ServiceName = "tmis-client-laboratory";
        public final String PortName = "client-laboratory";

    }

    public static interface Laboratory2Client {
        public final String Namespace = "http://korus.ru/tmis/client-laboratory";
        public final String ServiceName = "tmis-client-laboratory2";
        public final String PortName = "client-laboratory2";
    }
}
