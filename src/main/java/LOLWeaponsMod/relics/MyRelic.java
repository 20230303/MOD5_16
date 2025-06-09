package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

// 继承CustomRelic
public class MyRelic extends CustomRelic {// 继承CustomRelic, 自定义遗物类
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = ModHelper.makePath("MyRelic");
    // 图片路径（大小128x128，可参考同目录的图片）
    private static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/LOL_Relic.png";
    // 遗物未解锁时的轮廓。可以不使用。如果要使用，取消注释
    // private static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/MyRelic_Outline.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.DEPRECATED;// 遗物等级,这里设置为STARTER，初始遗物。废弃遗物等级为DEPRECATED。
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;// 音效: 平滑

    public MyRelic() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
        // super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new MyRelic();
    }

    // 遗物效果，可在此处添加代码 TODO
//    @Override
//    public void atBattleStart() {// 战斗开始时触发
//        //添加效果只需要在你需要触发时机的方法中书写效果即可。例如下方的方法表示战斗开始时抽一张牌。
//        super.atBattleStart();
//        this.addToBot(new DrawCardAction(1));
//    }
    @Override
    public void atTurnStart() {//每回合开始时触发,每回合开始时抽一张牌
        super.atTurnStart();//调用父类方法
        this.addToBot(new DrawCardAction(1));//抽一张牌
        flash();//闪光
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));//在玩家头顶上显示遗物

    }
}
