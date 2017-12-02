package me.zane.fairy;


import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Helldefender on 2017/12/1 for Fairy.
 * Function:单元测试验证过滤方法过滤效果以及时间效率
 * Description:使用单元测试框架JUnit
 */

public class GrepFilterTest {
    private static final String TAG = GrepFilterTest.class.getSimpleName();


    @Test
    public void testGrepData() {
        //GrepFilter grepFilter = Mockito.mock(GrepFilter.class);
        Gr grepFilter = new GrepFilter();
        String grep = "android";
        String rawContent =
                "<p><font color=\"#ffa726\">ASS2222AS1ADDAFDAF11}</p>" +
                        "<p><font color=\"#ffa726\">hahawahaha122113}</p>" +
                        "<p><font color=\"#ffa726\">flyview666}</p>" +
                        "<p><font color=\"#ffa726\">1epFilter = Mockito.mock(GrepFilter.class);11}</p>" +
                        "<p><font color=\"#ffa726\">1DAFepFiGrepFilter.ADFASDFSD11}</p>" +
                        "<p><font color=\"#ffa726\">ANDORVIepFock(GrepFilter.class);EW111}</p>" +
                        "<p><font color=\"#ffa726\">HUHUIII111}</p>" +
                        "<p><font color=\"#ffa726\">DIDLIDIDL111}</p>" +
                        "<p><font color=\"#ffa726\">BILIBI111}</p>" +
                        "<p><font color=\"#ffa726\">DDD1SDFASDFASDFSDADLDLDLDDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">testGrepDat11}</p>" +
                        "<p><font color=\"#ffa726\"> privatestaticfinalStringTAG=GrepF4456788111}</p>" +
                        "<p><font color=\"#ffa726\">ane.fairy.repository111}</p>" +
                        "<p><font color=\"#ffa726\">1aniry.rory11}</p>" + "<p><font color=\"#ffa726\">111}</p>" + "<p><font color=\"#ffa726\">111}</p>" + "<p><font color=\"#ffa726\">111}</p>" +
                        "<p><font color=\"#ffa726\">aeeetestGepDae111}</p>" +
                        "<p><font color=\"#ffa72DSFADFASFASDFDSF6\">111}</p>" + "<p><font color=\"#ffa726\">111}</p>" + "<p><font color=\"#ffa726\">111}</p>" +
                        "<p><font color=\"#ffa726\">qeeeeeeqqqqeeeeeeeqqqqqq1qqqq1eee1}</p>" +
                        "<p><font color=\"#ffa726\">qqqeeeeesssdsdfsdfsdfqqq1qqq11}</p>" + "<p><font color=\"#ffa726\">oggh;,,uhbggfgggF1oggh;,,uhbggfgggF11}</p>" + "<p><font color=\"#ffa726\">111}</p>" +
                        "<p><font color=\"#ffa726\">1aaafsdfssdsdfdsfsfd11}</p>" +
                        "<p><font color=\"#ffa726\">qqdsfsvatestaticfinalStringTAdfsdfsdfsdfqq111}</p>" + "<p><font color=\"#ffa726\">111}</p>" +
                        "<p><font color=\"#ffa726\">tttrtevatestaticfinalStringTAstGrepDatrttt11a1}</p>" + "<p><font color=\"#ffa726\">111}</p>" + "<p><font color=\"#ffa726\">111}</p>" +
                        "<p><font color=\"#ffa726\">iiiisdffffffffffii111}</p>" +
                        "<p><font color=\"#ffa726\">1qqvatestaticfinalStringTAqq11}</p>" + "<p><font color=\"#ffa726\">vatevatestaticfinalStringTAstaticfinalStringTA111}</p>" + "<p><font color=\"#ffa726\">11-27 03:57:}</p>" +
                        "<p><font color=\"#ffa726\">pppppp11a-27 03:57:}</p>" + "<p><font color=\"#ffa726\">aid}</p>" +
                        "<p><font color=\"#ffa726\">990oggh;,,uhbggfgggFASDFDS11}</p>" + "<p><font color=\"#ffa726\">rvatestaticfinalStringTAecyc</p>" +
                        "<p><font color=\"#ffa726\">1SDFASDFASDFSDASddddddASDFDS11}</p>" + "<p><font color=\"#ffa726\">1vatestaticfinalStringTASDFASDFASDFSDASviepagerDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">ddfgss566790ffggA-SDFSDDLDLDDLLDSDFASDFDS11}</p>" + "<p><font color=\"#ffa726\">1SDFASDF0A9SDFSDASDFADNDNDDLDLDLDLDLDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">anmima1SDFAS9DFASD9FhahabalidlbaaddDLDDLLDSDFASDFDS11}</p>" + "<p><font color=\"#ffa726\">jaddiviaenadjaljlanfsdnlafdiewpager}</p>" +
                        "<p><font color=\"#ffa726\">173SDFASDFASDFSD9ALLDSDFASDFDS11}</p>" + "<p><font color=\"#ffa726\">5133SDFASDFA3SDFS9DASDFADNDNDDLDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">1SDFASDFASDFSDASDL9DLDLDLDDSDFASDFDS11}</p>" + "<p><font color=\"#ffa726\">1SDFASDFASDFSDASD3F9ADNDNDDLDdddddecgffLDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">jdiidblid3pa9ger}</p>" + "<p><font color=\"#ffa726\">FADASDFADNDNDDLDLDL3DLLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">1SDFASDFASDFSDASDFADN9DNDDLDLDLDLDLDLDimagvbiewDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">1SDFANDDDLDDLLDS3D33FA93SDFDS11}</p>" + "<p><font color=\"#ffa726\">FASDNDNDDLDLDLDLDLDLDDL99LDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">hste222SDASDFSDASDFADNDNDDLDL9DLDLDLDLDimagevlistviewDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">33333SDFASDFASDFSDASDFADNDNDDLDLDLDLDLDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">qqmmyvatestaticfinalStringTAymmmmyvatestaticfinalStringTAvatestaticfinalStringTAyyy11a1}</p>" + "<p><font color=\"#ffa726\">1avatestaticfinalStringTAne.fairy.repository11}</p>" + "<p><font color=\"#ffa726\">111}</p>" +
                        "<p><font color=\"#ffa726\">nvatestaticfinalStringTAnttyyyynnn11a1}</p>" + "<p><font color=\"#ffa726\">111FFvatestaticfinalStringTAFF}</p>" + "<p><font color=\"#ffa726\">111}</p>" +
                        "<p><font color=\"#ffa726\">ooovatestaticfinalStringTAyyyyyo11a1}</p>" + "<p><font color=\"#ffa726\">11Fane.fairy.repositoryFFF1}</p>" + "<p><font color=\"#ffa726\">1FFFF11}</p>" +
                        "<p><font color=\"#ffa726\">ppppDFAvatestaticfinalStringTASDFASDFSDASDFADNDNDDLLDSDFASDFp11a1}</p>" + "<p><font color=\"#ffa726\">ry.repository11ane.fairy.repository1}</p>" +
                        "<p><font color=\"#ffa726\">}timenvatestaticfinalStringTAofaianGHHvatestaticfinalStringTAJJGTme </p>" +
                        "<p><font color=\"#ffa726\">android}</p>" +
                        "<p><font color=\"#ffa726\">1SDFASDFASDFSDASDFADNDNDDLLDSDFASDFDS11}</p>" + "<p><font color=\"#ffa726\">FFFoggh;,,uhbggfgggFFFFFoggh;,,uhbggfgggFFFFFFFFFF}</p>" +
                        "<p><font color=\"#ffa726\">jli97hhge31SDFASD;,,uhbggfgggFASDFDFASDFSDAd11SDLDLDLDLDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">1SDFASDFASDFSDASDFAD;,,uhbggfgggFASDFDNDNDDLDLDLDLDLDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">1SDFASDpassviewLD;,,uhbggfgggFASDFDLDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">javaBOBOAJAHONvatestat;,,uhbggfgggFASDFDicfinalStringTAEPEIid}</p>" +
                        "<p><font color=\"#ffa726\">1SDFASDFASDFSDASDFADNDNDDLDLDLDLDLDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">1SDFASDFASDFSDASDFADNDNDDLDLDLDLDLDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">1SDFASDSDFASDF;,,uhbggfgggFASDFD;,,uhbggfgggFASDFD;,,uhbggfgggFASDFD;,,uhbggfgggFASDFDDS11}</p>" +
                        "<p><font color=\"#ffa726\">DFADNDNDDLDLDLDLDLDLDDLLDSDDDDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">1SDFASDFDFoggh;,,uhbggfgggFDS11}</p>" + "<p><font color=\"#ffa726\">jaoggh;,,uhbggfgggFvieer}</p>" +
                        "<p><font color=\"#ffa726\">1SDFASDFAdddNDNDD;,,uhbggfgggFASDFDLFFFFLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">1SDFssssssssssssDDLLDSDFAS;,,uhbggfgggFASDFDDFDS11}</p>" + "<p><font color=\"#ffa726\">1SDFASDFASDFoggh;,,uhbggfgggFSDASDFADNDNDDLDLDLDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">990oggh;,,uhbggfgggFASDFDS11}</p>" + "<p><font color=\"#ffa726\">recyc</p>" +
                        "<p><font color=\"#ffa726\">1SDFASDFASDFSDASdoggh;,,uhbggfgggFdddddASDFDS11}</p>" + "<p><font color=\"#ffa726\">1SDFASDFASDFSDASviepagerDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">ddfgss566790ffggA-SDFSDDLDLDDLLDSDFASDFDS11}</p>" + "<p><font color=\"#ffa726\">1SDFASDF0A9SDFSDASDFADNDNDDLDLDLDLDLDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">1SDFASD0FAS9DFSDASDFADNDsndroxtviewSDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">anmi;,,uhbggfgggFASDFDma1SDFAS9DFASD9FhahabalidlbaaddDLDDLLDSDFASDFDS11}</p>" + "<p><font color=\"#ffa726\">jaddiviaenadjaljlanfsdnlafdiewpager}</p>" +
                        "<p><font color=\"#ffa726\">ilove133S8D;,,uhbggfgggFASDFDFASDFASD9FSDASDFADNDilidng;didiDLtextviewDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">6188SDFASDFASin9gDFSDASDFADNDNDDLDLDLDLDLDLDDdatabindingLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">173SDFASDFASDFSD9ALLDSDFASDFDS11}</p>" + "<p><font color=\"#ffa726\">5133SDFASDFA3SDFS9DASDFADNDNDDLDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">1SDFASDFASDFSDASDL9DLDLDLDDSDFASDFDS11}</p>" + "<p><font color=\"#ffa726\">1SDFASDFASDFSDASD3F9ADNDNDDLDdddddecgffLDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">jdiidblid3pa9ger}</p>" + "<p><font color=\"#ffa726\">FADASDFADNDNDDLDLDL3DLLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">1SDFASDFASDFSDASDFADN9DNDDLDLDLDLDLDLDimagvbiewDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">1SDFANDDDLDDLLDS3D33FA93SDFDS11}</p>" + "<p><font color=\"#ffa726\">FASDNDNDDLDLDLDLDLDLDDL99LDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">hste222SDASDFSDASDFADNDNDDLDL9DLDLDLDLDimagevlistviewDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">33333SDFASDFASDFSDASDFADNDNDDLDLDLDLDLDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">3344133SDFASDFASDFSDASDFA33DNDNDDLDLDLDLDLDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">g44111}</p>" + "<p><font color=\"#ffa726\">111}</p>" + "<p><font color=\"#ffa726\">111}</p>" + "<p><font color=\"#ffa726\">111}</p>" +
                        "<p><font color=\"#ffa726\">44111}</p>" + "<p><font color=\"#ffa726\">111}</p>" +
                        "<p><font color=\"#ffa726\">1SDFASDFASDFSDASDFADNDNDDLDLDLDLDLDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">DUDU1556551}</p>" + "<p><font color=\"#ffa726\">1SDFASDFASDFSDASDFADNDNDDLDLDLDLDLDLDDLLDSDFASDFDS11}</p>" +
                        "<p><font color=\"#ffa726\">BIBIBI9990900111}</p>" + "<p><font color=\"#ffa726\">111}</p>" +
                        "<p><font color=\"#ffa726\">1AHUANADFADJAJLA1}</p>" + "<p><font color=\"#ffa726\">111}</p>" + "<p><font color=\"#ffa726\">111}</p>" + "<p><font color=\"#ffa726\">111}</p>" +
                        "<p><font color=\"#ffa726\">8LKOPIPOPDJANDAJJDAKAJLDJLADFOEIEIJGN11DDD1}</p>" +
                        "<p><font color=\"#ffa726\">1#f55FFD;gFASDFDDHJJKKHGfa72611}</p>" +
                        "<p><font color=\"#ffa726\">#233fDD;,,uhbgDDDfa89lopghh11}</p>" +
                        "<p><font color=\"#ffa726\">1SDFASDFASDFSDASDFADNbaalonada;dlasd;LDLDLDLDDLLDSDFASDFDS11}</p>";

        String expectedRsult = "<p><font color=\"#ffa726\">android}</p>";
        long startTime = System.currentTimeMillis();
        Assert.assertEquals(expectedRsult, grepFilter.parseHtml1(rawContent, grep));
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
    }
}
