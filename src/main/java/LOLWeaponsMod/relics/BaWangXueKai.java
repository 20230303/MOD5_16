package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/**
 *   "LOLWeaponsMod:BaWangXueKai": {
 *     "NAME": "霸王血铠",
 *     "FLAVOR": "铠甲饮尽千人之血，终成不朽暴君的第二层皮肤。",
 *     "DESCRIPTIONS": ["每当你受到攻击时，获得1点力量，至多获得3点"]
 *   },
 */
public class BaWangXueKai extends CustomRelic {
    public static final String ID = ModHelper.makePath("BaWangXueKai");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/BaWangXueKai.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/BaWangXueKai.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.RARE;//类型:稀有
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//声音:重击
    public BaWangXueKai() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new BaWangXueKai();
    }
    // 遗物效果，可在此处添加代码 TODO
//    public int onAttacked(DamageInfo info, int damageAmount) {
//
//        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0) {
//            this.flash();
//            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
//            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 2), 2));
//        }
//
//        return damageAmount;
//    }
//    public int onAttacked(DamageInfo info, int damageAmount) {
//        // 检查伤害来源是否为敌人，并且伤害类型不是HP_LOSS或THORNS，伤害值大于0
//        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0) {
//            // 检查玩家当前的力量值是否已经达到最大限制
//            StrengthPower strengthPower = (StrengthPower) AbstractDungeon.player.getPower("Strength");
//            int currentStrength = strengthPower != null ? strengthPower.amount : 0;
//
//
//            if (AbstractDungeon.player.getPower("Strength").amount < 3) {
//                this.flash(); // 闪动特效
//                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this)); // 遗物图标显示在玩家上方
//                this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1)); // 增加1点力量
//            }
//        }
//        return damageAmount;
//    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        // 检查伤害来源是否为敌人，并且伤害类型不是HP_LOSS或THORNS，伤害值大于0
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0) {
            // 检查玩家当前的力量值是否已经达到最大限制
            StrengthPower strengthPower = (StrengthPower) AbstractDungeon.player.getPower("Strength");
            int currentStrength = strengthPower != null ? strengthPower.amount : 0;
            if (currentStrength < 3) {
                this.flash(); // 闪动特效
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this)); // 遗物图标显示在玩家上方
                this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1)); // 增加1点力量
            }
        }
        return damageAmount;
    }

}
