package com.packers.movers.test.utils;

public class TestXmlUtils {

    public static String getAppRec_1_0(String messageId, String originalMessageId) {
        return
            "<AppRec xmlns=\"http://www.kith.no/xmlstds/apprec/2004-11-21\">\n" +
            "  <MsgType V=\"APPREC\"/>\n" +
            "  <MIGversion>1.0 2004-11-21</MIGversion>\n" +
            "  <GenDate>2004-12-17T09:30:47</GenDate>\n" +
            "  <Id>" + messageId + "</Id>\n" +
            "  <Sender>\n" +
            "    <Role V=\"SENDER\"/>\n" +
            "    <HCP>\n" +
            "      <Inst>\n" +
            "        <Name>Kreftregisteret</Name>\n" +
            "        <Id>974771381</Id>\n" +
            "        <TypeId V=\"ENH\"/>\n" +
            "      </Inst>\n" +
            "      <AddressContract>\n" +
            "        <Type V=\"PST\"/>\n" +
            "        <TeleAddress V=\"mailto:edi@kreftregisteret.no\"/>\n" +
            "      </AddressContract>\n" +
            "    </HCP>\n" +
            "  </Sender>\n" +
            "  <Receiver>\n" +
            "    <Role V=\"RECEIVER\"/>\n" +
            "    <HCP>\n" +
            "      <HCProf>\n" +
            "        <Name>Magnar Koman</Name>\n" +
            "        <Id>123</Id>\n" +
            "        <TypeId V=\"HER\"/>\n" +
            "      </HCProf>\n" +
            "      <AddressContract>\n" +
            "        <Type V=\"PST\"/>\n" +
            "        <TeleAddress V=\"mako@bottomline.no\"/>\n" +
            "      </AddressContract>\n" +
            "    </HCP>\n" +
            "  </Receiver>\n" +
            "  <Status DN=\"OK\" V=\"1\"/>\n" +
            "  <OriginalMsgId>\n" +
            "    <MsgType DN=\"Meldeskjema brystkreft\" V=\"CANCER_BR\"/>\n" +
            "    <IssueDate>2004-12-17T09:32:15</IssueDate>\n" +
            "    <Id>" + originalMessageId + "</Id>\n" +
            "  </OriginalMsgId>\n" +
            "</AppRec>";
    }

    public static String getAppRec_1_1(String messageId, String originalMessageId) {
        return
            "<AppRec xmlns=\"http://www.kith.no/xmlstds/apprec/2012-02-15\">\n" +
            "  <MsgType V=\"APPREC\"/>\n" +
            "  <MIGversion>1.0 2004-11-21</MIGversion>\n" +
            "  <GenDate>2016-09-26T11:45:47.9582975Z</GenDate>\n" +
            "  <Id>" + messageId + "</Id>\n" +
            "  <Sender>\n" +
            "    <Role V=\"SENDER\"/>\n" +
            "    <HCP>\n" +
            "      <Inst>\n" +
            "        <Id>974771381</Id>\n" +
            "        <TypeId V=\"ENH\"/>\n" +
            "      </Inst>\n" +
            "    </HCP>\n" +
            "  </Sender>\n" +
            "  <Receiver>\n" +
            "    <Role V=\"RECEIVER\"/>\n" +
            "    <HCP>\n" +
            "      <HCProf>\n" +
            "        <Name>Test Testsson</Name>\n" +
            "        <Id>123</Id>\n" +
            "        <TypeId V=\"HER\"/>\n" +
            "      </HCProf>\n" +
            "      <AddressContract>\n" +
            "        <Type V=\"PST\"/>\n" +
            "        <TeleAddress V=\"com.packers.movers.test@com.packers.movers.test.com\"/>\n" +
            "      </AddressContract>\n" +
            "    </HCP>\n" +
            "  </Receiver>\n" +
            "  <Status DN=\"OK\" V=\"1\"/>\n" +
            "  <OriginalMsgId>\n" +
            "    <MsgType DN=\"Legeoppgjørsmelding\" V=\"LOM\"/>\n" +
            "    <IssueDate>2016-09-25T11:45:47.9592978Z</IssueDate>\n" +
            "    <Id>" + originalMessageId + "</Id>\n" +
            "  </OriginalMsgId>\n" +
            "</AppRec>";
    }
}
