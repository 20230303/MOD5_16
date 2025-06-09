package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import basemod.devcommands.power.Power;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/**
 *   "LOLWeaponsMod:ShenShengZhiJian": {
 *     "NAME": "神圣之剑",
 *     "FLAVOR": "剑刃折射天界辉光，黑暗在触碰前便已蒸发。",
 *     "DESCRIPTIONS": ["战斗开始时，获得一层仪式"]
 *   },
 */
public class ShenShengZhiJian extends CustomRelic {
    public static final String ID = ModHelper.makePath("ShenShengZhiJian");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/ShenShengZhiJian.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/ShenShengZhiJian.png";
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;//类型：稀有
    private static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;//音效：魔法
    public ShenShengZhiJian() {
        //super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new ShenShengZhiJian();
    }
    // 遗物效果，可在此处添加代码 TODO
    public void atBattleStart() {//战斗开始时触发，
        addToTop(new ApplyPowerAction( AbstractDungeon.player, AbstractDungeon.player, new RitualPower(AbstractDungeon.player, 1, true), 1));
    }
}
