package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/**
 *   "LOLWeaponsMod:BingChuanYuanDun": {
 *     "NAME": "冰川圆盾",
 *     "FLAVOR": "寒霜在盾面蔓延，冻结每一次心跳。",
 *     "DESCRIPTIONS": ["每回合开始，对所有敌人施加1层虚弱。"]
 *   },
 */
public class BingChuanYuanDun extends CustomRelic {
    public static final String ID = ModHelper.makePath("BingChuanYuanDun");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/BingChuanYuanDun.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/BingChuanYuanDun.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.UNCOMMON;//类型:BOSS
    //所有类型:COMMON,UNCOMMON,RARE,SPECIAL,BOSS,SHOP,DEPRECATED,STARTER
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//声音:魔法


    public BingChuanYuanDun() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new BingChuanYuanDun();
    }
    // 遗物效果，可在此处添加代码 TODO
    public void atTurnStart() {//每回合开始时触发
        flash();
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            addToBot(new RelicAboveCreatureAction(mo, this));
            addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, 1, false), 1, true));
        }
    }
}
