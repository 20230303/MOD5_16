package LOLWeaponsMod.relics;


import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import basemod.devcommands.power.Power;
import com.brashmonkey.spriter.Player;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/**
 * 遗物：三项之力
 * 效果：第三回合开始时，获得3点力量、敏捷、集中、能量、格挡、多层护甲、活力、人工制品、真言、荆棘
 * */
public class SanXiangZhiLi extends CustomRelic {
    public static final String ID = ModHelper.makePath("SanXiangZhiLi");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/SanXiangZhiLi.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/SanXiangZhiLi.png";
    private static final RelicTier RELIC_TIER = RelicTier.BOSS;//类型:BOSS
    //所有类型:COMMON,UNCOMMON,RARE,SPECIAL,BOSS,SHOP,DEPRECATED,STARTER
    private static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;//声音:魔法
    public SanXiangZhiLi() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new SanXiangZhiLi();
    }
    // 遗物效果，可在此处添加代码 TODO

    public void onEquip(){//拾取时触发
        CardCrawlGame.sound.play("GOLD_GAIN");//播放音效
        AbstractDungeon.player.gainGold(33);//获得33金币
        AbstractDungeon.player.increaseMaxHp(3, true);//获得3点最大生命
    }
    public void atBattleStart() {//战斗开始时触发，
        this.counter = 0;
    }
    public void atTurnStart() {
        if (!this.grayscale) {
            this.counter++;
        }
        if (this.counter == 3) {//计数器为3时触发效果
            flash();//闪烁
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));//在玩家头上出现
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 3), 3));//获得3点力量
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 3), 3));//获得3点敏捷
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, 3), 3));//获得3点集中
            addToTop(new GainEnergyAction(3));//获得3点能量
            addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 3));//获得3点格挡
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, 3), 3));//获得3点活力
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, 3), 3));//获得3点多层护甲
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, 3), 3));//获得3点人工制品
            addToTop(new ApplyPowerAction(AbstractDungeon.player, null, new MantraPower(AbstractDungeon.player, 3), 3));//获得3点真言
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, 3), 3));//获得3点荆棘
            this.counter = -1;
            this.grayscale = true;//变灰
        }
    }
    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
    }


    /**船舵的源码，可以参考
     * 第三回合开始时，获得18点格挡
     */
//    public void atBattleStart() {
//        this.counter = 0;
//    }
//
//    public void atTurnStart() {
//        if (!this.grayscale) {
//            this.counter++;
//        }
//        if (this.counter == 3) {
//            flash();
//            addToBot(new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, this));
//            addToBot(new GainBlockAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature) AbstractDungeon.player, 18));
//            this.counter = -1;
//            this.grayscale = true;
//        }
//    }
//
//    public void onVictory() {
//        this.counter = -1;
//        this.grayscale = false;
//    }

}
