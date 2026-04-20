/*
 * Copyright (C) 2026 yztz
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package top.yztz.msggo.util;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

import com.github.houbb.sensitive.word.core.SensitiveWordHelper;

/**
 * Unit tests for SensitiveWordUtil.
 */
public class SensitiveWordUtilTest {

    @Test
    public void testContains_withSensitiveWord_returnsTrue() {
        String[] texts = {
            "日入10w", "luo聊", "衣果聊", "衣果耳卯", "贷款", "簧片", "杀人放火", "你妈死了",
                "傻逼", "色~@!情", "色~@!！情", "黄色视频", "枪支", "弹药", "草他妈的", "傻子", "笨蛋", "作弊"
        };
        // These texts are expected to contain sensitive words, so the test should assert that contains() returns true for each of them.
        for (String text : texts) {
            assertTrue("Should detect sensitive words in text:\n\n"
                            + text + "\n\ncontains: \n\n"
                            + SensitiveWordUtil.findAll(text),
                    SensitiveWordUtil.contains(text));
        }
    }

    @Test
    public void testContains_withNormalText_returnsFalse() {
        String[] texts = { // These texts are expected to be normal and should not contain sensitive words, so the test should assert that contains() returns false for each of them.
                "have",
                "生活用品", // Daily necessities
                "考试用品", // Exam supplies
                "账号密码登录", // Account password login
                "参加笔试", // Participate in written test
                "参加考试", // Participate in exam
                "参加面试", // Participate in interview
                "电话面试", // Phone interview
                "远程面试", // Remote interview
                "期待光临", // Looking forward to your visit
                "您的包裹已由[张大宝]签收，如有疑问请联系", // Your package has been signed for by [Zhang Dabao], if you have any questions please contact
                "我把我的视频会员账号发你：账号123456，密码是Aa123456，你自己登上去看", // I will send you my video membership account: account 123456, password is Aa123456, you can log in and watch
                "【物业通知】各位业主：由于水管紧急维修，本小区2号楼1、2单元将于下午2点起停水，请大家提前做好储水准备。", // [Property Notice] Dear residents: Due to emergency water pipe repairs, Building 2, Units 1 and 2 of our community will experience water outages starting at 2 PM. Please prepare for water storage in advance.
                "【居委会】温馨提示：本周六上午将在小广场开展全民反诈宣传活动，现场有精美礼品发放，欢迎踊跃参加。", // [Neighborhood Committee] Warm reminder: This Saturday morning, there will be a nationwide anti-fraud publicity event at the small square, with exquisite gifts distributed on site. Welcome to participate enthusiastically.
                "【XX学校】家长您好，本学期期末考试成绩单已通过内网发布，请登录家校通平台查询孩子表现，切勿点击不明链接。", // [XX School] Dear parents, the report cards for this semester's final exams have been published through the intranet. Please log in to the school communication platform to check your child's performance, and do not click on any unknown links.
                "通知：下周三上午9点至11点将入户进行燃气安全检查，请家中留人。", // Notice: Gas safety inspections will be conducted door-to-door next Wednesday from 9 AM to 11 AM. Please have someone at home.
                "尊敬的用户，您本月电费余额已不足10元，为避免停电给您带来不便，请及时通过微信或支付宝缴纳。", // Dear user, your electricity bill balance for this month is less than 10 yuan. To avoid inconvenience caused by power outages, please make a payment through WeChat or Alipay in a timely manner.
                "哈喽[名字]，这周末有空吗？想约你一起去[地点][做某事]，有兴趣的话跟我说声哈！", // Hello [Name], are you free this weekend? I want to invite you to [location][do something], if you're interested, let me know!
                "[领导姓名]您好，我因[身体不适/家中有事]，今日想请假一天。工作上的急事可以随时微信/电话联系我，请批准。", // Hello [Leader's Name], I would like to request a day off today due to [physical discomfort/ family matters]. You can contact me anytime via WeChat/phone for urgent work matters. Please approve.
                "[姓名]您好，提醒一下我们约了[时间]开会。会议链接/地点是[信息]，期待您的参与。", // Hello [Name], just a reminder that we have a meeting scheduled for [time]. The meeting link/location is [information], looking forward to your participation.
                "谢谢你的邀请！真的很想去，但我那天正好有其他安排了。下次有机会我们再约，祝你们玩得开心！", // Thank you for the invitation! I really want to go, but I happen to have other plans that day. Let's plan for another time, and I hope you all have a great time!
                "[姓名]，平时忙碌少联系，但在[节日名]这一天特别想送个祝福。祝你和家人节日快乐，万事顺意！", // [Name], we don't keep in touch often due to busy schedules, but on this [Holiday Name] I especially want to send my blessings. Wishing you and your family a happy holiday and all the best!
                "[姓名]，祝你[节日名]快乐！希望你新的一年/这个月 每天都有好心情，工作顺利，平安喜乐。", // [Name], wishing you a happy [Holiday Name]! I hope every day in the new year/this month brings you good mood, smooth work, and peace and joy.
                "谁说明信片才算心意？这条短信装满了我的祝福。祝[姓名][节日名]快乐，吃好喝好，不长胖！", // Who says postcards are the only way to show sincerity? This text message is filled with my blessings. Wishing [Name] a happy [Holiday Name], eat well, drink well, and don't gain weight!
                "%s您好，我是[你的名字]。感谢您一直以来的信任。值此佳节，祝您生意兴隆，身体健康。有任何需求随时联系。", // %s Hello, I am [Your Name]. Thank you for your continued trust. On this festive occasion, I wish you prosperous business and good health. Feel free to contact me anytime if you have any needs.
                "各位好，很高兴能在[活动名]上认识大家。附件是本次会议的资料汇总，希望能对大家有所帮助。期待未来合作。", // Hello everyone, it's great to meet you at [Event Name]. Attached is a summary of the materials from this meeting, I hope it can be helpful to everyone. Looking forward to future cooperation.
                "嘿 %s，我是[你的名字]。这是我的新手机号，原号码即将停用。方便的话请存一下，保持联系哈！", // Hey %s, I am [Your Name]. This is my new phone number, the original number will soon be deactivated. Please save it if it's convenient, let's keep in touch!
                "各位好，我搬新家啦！打算在[日期]举办个小型暖房聚会，诚邀大家来坐坐。地点：[地址]，收到的请回复一下。", // Hello everyone, I have moved to a new home! I plan to host a small housewarming party on [Date], and sincerely invite everyone to come. Location: [Address], please reply if you receive this.
                "各位朋友好，我已于近日入职[公司名]担任[职位]。以后还请多多关照，有空常聚！", // Hello friends, I have recently joined [Company Name] as [Position]. Please take care of me in the future, and let's get together often when we have time!
                "%s您好，之前跟您沟通的[事项/合同]不知进度如何了？如果有需要我配合的地方请随时告知。", // %s Hello, I wonder how the progress is on the [matter/contract] we discussed before? If there is anything I can assist with, please let me know anytime.
                "【提醒】%s您好，明天就是[截止日期]了，关于[项目名]的资料还没收到，麻烦您抽空处理下哈。", // [Reminder] %s Hello, tomorrow is the [deadline], and we haven't received the materials for [project name] yet. Please take some time to handle it.
                "%s您好，很抱歉打扰，请问上次提到的款项是否已经安排入账？以便我们这边财务对账。", // %s Hello, sorry to bother you, may I ask if the payment we mentioned last time has been arranged for accounting? This will help our finance department with reconciliation.
                "【离职感谢】各位同事，今天是我在[公司名]的最后一天。感谢大家这段时间对%s的照顾，青山不改，绿水长流，江湖再见！", // [Resignation Thanks] Dear colleagues, today is my last day at [Company Name]. Thank you all for taking care of %s during this time. May we meet again in the future!
                "【晋升答谢】感谢%s这段时间对我的指导和提拔，没有您的帮助我无法取得今天的进步。今后我会更加努力！", // [Promotion Thanks] Thank you %s for your guidance and promotion during this time. Without your help, I wouldn't have made the progress I have today. I will work even harder in the future!
                "各位伙伴，我已正式入职[新公司]，负责[业务]。希望能与各位展开新的合作，联系方式不变。", // Hello everyone, I have officially joined [New Company], responsible for [Business]. I hope to start new cooperation with all of you, and my contact information remains unchanged.
                "一点小小心意，祝%s节日快乐，每天都要开心呀！", // A little token of my affection, wishing %s a happy holiday, and may you be happy evey day!
                "【入伙礼】祝贺%s乔迁新居！祝新家新气象，日子红红火火！", // [Housewarming Gift] Congratulations to %s on moving to a new home! Wishing you a fresh start and a prosperous life in your new home!
                "恭喜%s喜添贵子/千金！祝小宝贝健康成长，聪明伶俐。", // Congratulations to %s on the birth of your son/daughter! Wishing the little baby healthy growth and intelligence.
                "%s，好久没聚了，这周末有空一起出来吃个饭吗？位置你挑，我买单！", // %s, it's been a while since we last got together. Are you free this weekend to go out for a meal? You choose the place, it's on me!
                "呼叫%s！最近发现一家超赞的[餐厅类型]，有没有兴趣去打个卡？", // Calling %s! I recently found an amazing [type of restaurant], are you interested in checking it out?
                "%s，今晚我们几个老伙计打算小聚一下，在[地点]，能来不？等你好消息。", // %s, tonight a few of us old buddies are planning to get together at [location], can you make it? Waiting for your good news.
                "尊敬的（${姓名}同学）家长：我是江苏XXXXXXX学校XXXXXX年级3班班主任张三，您可以通过短信或者电话与我们联系了解学生在校情况。班主任电：张三  15111111111    办公室电话：0111-22222222 期待通过我们的共同努力，家校携手，为孩子的成长保驾护航。祝您和您的家庭身体健康，生活如意！收到请回复一下，谢谢！", // Dear (Parent of ${Name}): I am Zhang San, the homeroom teacher of Class 3, Grade X at Jiangsu XXXXXXX School. You can contact us via SMS or phone to learn about the student's situation at school. Homeroom teacher phone: Zhang San 15111111111 Office phone: 0111-22222222 We look forward to working together to ensure the growth and development of your child. Wishing you and your family good health and a happy life! Please reply if you received this, thank you!
                "【%s社团】同学你好！恭喜你通过初筛，请于%s前往%s参加面试，期待你的表现！", // [Club Name] Hello student! Congratulations on passing the initial screening. Please go to [location] for an interview before [time], looking forward to your performance!
                "【%s】终于等到你！恭喜你正式成为我们的一员。请准时参加今晚%s的迎新会，不见不散！", // [Club Name] Finally, we have been waiting for you! Congratulations on officially becoming one of us. Please attend the welcome party tonight at [time], see you there!
                "嘿！想让大学生活更精彩吗？%s招新倒计时最后24小时！报名地点：%s，等一个有才华的你。", // Hey! Want to make your college life more exciting? The countdown for %s recruitment is in the last 24 hours! Registration location: %s, waiting for a talented you.
                "【会议通知】请%s全体成员于%s在%s准时开会，讨论%s相关事宜，收到请回复。", // [Meeting Notice] All members of %s, please attend a meeting at [time] in [location] to discuss matters related to [topic]. Please reply if you received this.
                "【重要通知】关于%s活动的策划案已发至群文件，请各位于%s前查看并反馈意见。——%s负责人", // [Important Notice] The planning proposal for the %s event has been sent to the group files. Please review and provide feedback before [time]. - %s Person in charge ==>
                "提醒：各部门部长请于今日%s到%s领取活动物资，收到请回复确认。", // Reminder: Department heads, please go to [location] to collect event materials at [time] today. Please reply to confirm receipt.
                "【紧急变动】各位同学，原定于%s举办的%s活动因天气/场地原因改至%s举行，请互相转告！", // [Urgent Change] Dear students, the %s event originally scheduled for [time] has been moved to [location] due to weather/venue reasons. Please spread the word!
                "【提醒】%s活动即将于一小时后在%s开始，请工作人员提前20分钟到场签到。", // [Reminder] The %s event will start in one hour at [location]. Staff members, please arrive 20 minutes early for check-in.
                "不好意思打扰大家，由于临时状况，今晚的%s取消，具体补办时间另行通知，请见谅。", // Sorry to bother everyone, due to unforeseen circumstances, tonight's %s event is canceled. The specific make-up time will be announced later. Please understand.
                "【感谢】本次%s活动圆满结束！感谢%s每位小伙伴的辛苦付出，大家辛苦了，早点休息！", // [Thank You] The %s event has successfully concluded! Thanks to every member of %s for your hard work, you all did a great job, get some rest early!
                "活动复盘提醒：请各位成员在%s前将%s总结发送至邮箱，大家的反馈对我们很重要。", // Event review reminder: Please send your %s summary to the email before [time]. Your feedback is very important to us.
                "精彩瞬间：%s活动的合影已上传，感谢%s的参与，期待下一次更精彩的相遇！", // Wonderful moments: The group photo of the %s event has been uploaded. Thanks to everyone in %s for your partcipation, looking forward to an even more exciting encounter next time!
        };
        for (String text : texts) {
            assertFalse("Should not detect sensitive words in normal text:\n\n"
                            + text + "\n\ncontains: \n\n"
                            + SensitiveWordUtil.findAll(text),
                    SensitiveWordUtil.contains(text));
        }
    }

    @Test
    public void testContains_withEmptyString_returnsFalse() {
        assertFalse("Empty string should return false", SensitiveWordUtil.contains(""));
    }

    @Test
    public void testContains_withNull_returnsFalse() {
        assertFalse("Null should return false", SensitiveWordHelper.contains(null));
    }

    @Test
    public void testFindAll_withMultipleSensitiveWords_returnsAll() {
        String text = "中奖"; // "Winning a prize" - this is a common sensitive word in Chinese contexts
        List<String> words = SensitiveWordUtil.findAll(text);
        
        assertNotNull("Result should not be null", words);
        assertFalse("Should find sensitive words", words.isEmpty());
        System.out.println("Found sensitive words: " + words);
    }

    @Test
    public void testFindAll_withNormalText_returnsEmptyList() {
        String text = "[对方姓名/称呼]您好，衷心感谢您一直以来的支持与信任。祝您及家人节日快乐，事业蒸蒸日上，阖家幸福安康！";
        // Hello [Recipient's Name/Title], I sincerely thank you for your continued support and trust. Wishing you and your family a happy holiday, prosperous career, and good health!
        List<String> words = SensitiveWordUtil.findAll(text);
        
        assertNotNull("Result should not be null", words);
        assertTrue("Should return empty list for normal text", words.isEmpty());
    }

//    @Test
//    public void testFindFirst_withSensitiveWord_returnsFirst() {
//        String text = "中奖"; // Winning a prize
//        String first = SensitiveWordUtil.findFirst(text);
//
//        assertNotNull("Should find first sensitive word", first);
//        System.out.println("First sensitive word: " + first);
//    }

//    @Test
//    public void testFindFirst_withNormalText_returnsNull() {
//        String text = "普通文本内容"; // "Normal-text content"
//        String first = SensitiveWordUtil.findFirst(text);
//
//        // findFirst returns empty string when no match, not null
//        assertTrue("Should return null or empty for normal text",
//                   first == null || first.isEmpty());
//    }
}
