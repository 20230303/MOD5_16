package LOLWeaponsMod.relics;


import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;



/**
 * 遗物：星蚀
 * 效果：每回合，每使用两张牌，获得2点格挡，对所有敌人造成2点伤害。
 * */
public class XingShi extends CustomRelic {
    public static final String ID = ModHelper.makePath("XingShi");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/XingShi.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/XingShi.png";
    private static final RelicTier RELIC_TIER = RelicTier.RARE;//类型：稀有
    private static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;//音效：魔法
    public XingShi() {
        //super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
         super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new XingShi();
    }
    // 遗物效果，可在此处添加代码 TODO
    @Override
    public void atTurnStart() {//每回合开始时重置计数器
        this.counter = 0;
    }
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //if (card.type == AbstractCard.CardType.ATTACK) {
        //不再限制使用卡牌类型，允许所有类型卡牌触发效果
        if (true) {
            this.counter++;
            if (this.counter % 2 == 0) {
                flash();
                this.counter = 0;
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));//将遗物放置在玩家身上
                addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 2));//获得2点格挡
                addToBot(new DamageAllEnemiesAction(null,//对所有敌人造成2点伤害
                        DamageInfo.createDamageMatrix(2, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HEAVY));
            }
        }
    }
    /*开信刀的源码，可以参考
    每回合，每使用3张技能牌，对所有敌人造成5点伤害。*/
//    public void atTurnStart() {
//    this.counter = 0;
//    }
//    public void onUseCard(AbstractCard card, UseCardAction action) {
//
//        if (card.type == AbstractCard.CardType.SKILL) {
//            this.counter++;
//            if (this.counter % 3 == 0) {
//                flash();
//                this.counter = 0;
//                addToBot((AbstractGameAction) new RelicAboveCreatureAction((AbstractCreature) AbstractDungeon.player, this));
//                addToBot((AbstractGameAction) new DamageAllEnemiesAction(null,
//                        DamageInfo.createDamageMatrix(5, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HEAVY));
//            }
//        }
//    }

    /*折扇的源码，可以参考
    每回合，每使用3张攻击牌，获得4点格挡。*/
//    public void atTurnStart() {
//        this.counter = 0;
//    }
//
//    public void onUseCard(AbstractCard card, UseCardAction action) {
//        if (card.type == AbstractCard.CardType.ATTACK) {
//            this.counter++;
//
//            if (this.counter % 3 == 0) {
//                flash();
//                this.counter = 0;
//                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
//                addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 4));
//            }
//        }
//    }



}
