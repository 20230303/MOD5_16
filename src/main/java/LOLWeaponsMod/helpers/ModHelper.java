package LOLWeaponsMod.helpers;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**简化卡牌ID
 * 牌ID每张都要写前缀太麻烦了。
 * 02 - 添加新卡牌
 * 为了和其他mod区分开来，你需要在ID之前加上你的modid前缀。
 * 为了偷懒，你可以写一个帮手方法。首先创建一个帮手类
 * 通过调用这个方法，你就只需要写实际的ID了
 */
public class ModHelper {
    public static String makePath(String id) {
        return "LOLWeaponsMod:" + id;
    }
    // 其他mod调用这个方法，传入实际的ID，返回带前缀的ID

    public static void addToBotAbstract(Lambda func) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                func.run();
                isDone = true;
            }
        });
    }

    public static void addToTopAbstract(Lambda  func) {
        AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                func.run();
                isDone = true;
            }
        });
    }


    public interface Lambda extends Runnable {}


}
