package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import LOLWeaponsMod.powers.GangPower;
import basemod.abstracts.CustomRelic;
import basemod.devcommands.power.Power;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/**
 * "LOLWeaponsMod:XinZhiGang": {
 *     "NAME": "心之钢",
 *     "FLAVOR": "每一次心跳都是战鼓，每一次搏动都是力量的积蓄。",
 *     "DESCRIPTIONS": [
 *       "第三回合开始时，对所有敌人施加1层 #ylolweaponsmod:庞然吞食"
 *     ]
 *   },
 */
public class XinZhiGang extends CustomRelic {
    public static final String ID = ModHelper.makePath("XinZhiGang");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/XinZhiGang.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/XinZhiGang.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.BOSS;//类型:稀有
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//声音:重击
    public XinZhiGang() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new XinZhiGang();
    }
    // 遗物效果，可在此处添加代码 TODO
    private static final int TURN_ACTIVATION = 3;//每3回合触发一次
    public void atBattleStart() {//战斗开始时触发，
        this.counter = 0;
    }
    public void onEquip() {//拾取时触发
        AbstractDungeon.player.decreaseMaxHealth(50);//失去3最大生命值
    }
    public void atTurnStart() {//回合开始时触发,计数器加1
        if (!this.grayscale) {
            this.counter++;
        }
        if (this.counter == 3) {//计数器为3时触发效果,对所有敌人施加1层 GangPowder DEBUFF
            flash();
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction((AbstractCreature)mo, this));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction((AbstractCreature)mo, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new GangPower((AbstractCreature)mo, 1), 1, true));
            }
            this.counter = -1;
            this.grayscale = true;
        }
    }

//    public void atBattleStart() {
//        this.counter = 0;
//    }
//    public void atTurnStart() {
//        if (!this.grayscale) {
//            this.counter++;
//        }
//        if (this.counter == 3) {//计数器为3时触发效果,对所有敌人施加1层 GangPowder DEBUFF
//            this.flash();
//            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
//                addToBot((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)mo, this));
//                addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)mo, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new GangPower((AbstractCreature)mo, 1), 1, true));
//            }
//
//
//            this.counter = -1;
//            this.grayscale = true;
//        }
//    }
    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
    }
}
