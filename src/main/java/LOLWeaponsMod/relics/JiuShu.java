package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.RedSkull;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

/**
 * "LOLWeaponsMod:JiuShu": {
 *     "NAME": "救赎",
 *     "FLAVOR": "当绝望笼罩战场，圣光将刺破阴霾。",
 *     "DESCRIPTIONS": ["生命值低于10时，获得3点再生"]
 *   },
 */
public class JiuShu extends CustomRelic {
    public static final String ID = ModHelper.makePath("JiuShu");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/JiuShu.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/JiuShu.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.BOSS;//类型:BOSS
    //所有类型:COMMON,UNCOMMON,RARE,SPECIAL,BOSS,SHOP,DEPRECATED,STARTER
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//声音:魔法


    public JiuShu() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }

    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new JiuShu();
    }

    // 遗物效果，可在此处添加代码 TODO
    private boolean isActive = false;//是否激活

//    @Override
//    public void atBattleStart() {
//        this.isActive = false;
//        this.pulse = false;
//        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, 0), 0));
//        AbstractDungeon.onModifyPower();
//    }

    @Override
//    public void update() {
//        super.update();
//        if (!isActive && AbstractDungeon.player.currentHealth <= 10) {
//            flash();
//            pulse = true;
//            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, 3), 3));
//            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
//            isActive = true;
//            AbstractDungeon.onModifyPower();
//        }
    //注释这段代码，则即便生命值大于10，也不会失去再生能力
//        else if (isActive && AbstractDungeon.player.currentHealth > 10) {
//            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, -3), -3));
//            stopPulse();
//            isActive = false;
//            AbstractDungeon.onModifyPower();
//        }
    //备用方案，回合开始时，检查生命值，获得3点再生能力
    public void atTurnStart() {//回合开始时，检查生命值，获得3点再生能力
        AbstractPlayer p = AbstractDungeon.player;
        if (AbstractDungeon.player.currentHealth <= 30) {
            flash();
            addToBot(new ApplyPowerAction(p, p, new RegenPower(p, 3), 3));
        }
    }

//
//    @Override
//    public void onVictory() {//战斗胜利时，失去再生能力
//        pulse = false;
//        isActive = false;
//        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, -3), -3));
//        AbstractDungeon.onModifyPower();
//    }
}

    /*
    // 原版游戏代码,再生药水，使用后，获得5点再生
    public void use(AbstractCreature target) {
    if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
    addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new RegenPower((AbstractCreature)AbstractDungeon.player, this.potency), this.potency));
    }
    }
     public int getPotency(int ascensionLevel) {
        return 5;
    }
     */


