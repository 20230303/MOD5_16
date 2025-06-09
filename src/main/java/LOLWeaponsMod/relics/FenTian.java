package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PenNibPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PenNib;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

/**
 * LOLWeaponsMod:FenTian": {
 *     "NAME": "焚天",
 *     "FLAVOR": "当天空开始燃烧，众生方知自己不过是薪柴。",
 *     "DESCRIPTIONS": ["每回合，对BOSS的第一次攻击造成双倍伤害，恢复4点生命"]
 *   },
 */
public class FenTian extends CustomRelic {
    public static final String ID = ModHelper.makePath("FenTian");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/FenTian.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/FenTian.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.BOSS;//类型:稀有
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//声音:重击
    public FenTian() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new FenTian();
    }
    // 遗物效果，可在此处添加代码 TODO
    private static final int _HP = 4;// 回复4点生命
    // BOSS战检测（参考奴隶项圈逻辑）
    private boolean isBossBattle() {
        if (AbstractDungeon.getCurrRoom() == null) return false;
        if (AbstractDungeon.dungeonMapScreen.map.atBoss) return true;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m != null && !m.isDead && m.type == AbstractMonster.EnemyType.BOSS) {
                return true;
            }
        }
        return false;
    }
    // 每回合开始时添加PenNibPower（仅BOSS战生效）
    @Override
    public void atTurnStart() {
        if (isBossBattle()) {
            AbstractDungeon.actionManager.addToTop(
                    new ApplyPowerAction(
                            AbstractDungeon.player,
                            AbstractDungeon.player,
                            new PenNibPower(AbstractDungeon.player, 1),
                            1
                    )
            );
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.player.heal(_HP);
        }
    }



    // 战斗结束时额外清除（确保非BOSS战不残留）
    @Override
    public void onVictory() {
        // 检查是否存在原生钢笔尖遗物
        if (AbstractDungeon.player.hasRelic(PenNib.ID)) {
            // 保留原生钢笔尖的PenNibPower（可能在非BOSS战中生效）
            return;
        }
        AbstractDungeon.actionManager.addToTop(
                new RemoveSpecificPowerAction(
                        AbstractDungeon.player,
                        AbstractDungeon.player,
                        PenNibPower.POWER_ID
                )
        );
    }






//    @Override
//    public void atBattleStart() {
//        if (AbstractDungeon.dungeonMapScreen.map.atBoss) {//如果在boss战中
//            this.flash();// 闪烁
//            this.grayscale = false;// 取消灰色
//        }else {
//            this.grayscale = true;// 灰色
//        }
//    }
//    @Override
//    public void onUseCard(AbstractCard card, UseCardAction action) {// 卡牌效果
//        if(!this.grayscale){// 非灰色状态
//            AbstractDungeon.player.hand.refreshHandLayout();
//            addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new PenNibPower((AbstractCreature)AbstractDungeon.player, 1), 1, true));
//
//        if (card.type == AbstractCard.CardType.ATTACK && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
//            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, card.damage * 2, card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
//            AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 4));
//        }
//        }
//    }
}
