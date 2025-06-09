package LOLWeaponsMod.cards;

import LOLWeaponsMod.action.ExampleAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.watcher.TriggerMarksAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;

public class Reconstruction extends CustomCard {
    //通常，你的卡牌名和类名是一致的，所以直接获取类名即可，此外还可以骗过文本编辑器，让它在复制时直接修改类名，就不用修改ID了。
    public static final String ID = Reconstruction.class.getSimpleName();
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    // 从游戏系统读取本地化资源

    //private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/cards/skill/Reconstruction.png";
    private static final int COST = 0;// 卡牌的初始费用
    //特殊的：-2费不显示能量图标（如诅咒卡状态卡等），-1费为X费（旋风斩等）
    //private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    // 读取本地化的描述

    private static final CardType TYPE = CardType.SKILL;// 卡牌的类型，这里是攻击类型.卡牌类型可以同时存在多个吗？
    //ATTACK：攻击类型；SKILL：技能类型；POWER：力量类型；STATUS：状态类型；CURSE：诅咒类型；
    private static final CardColor COLOR = CardColor.COLORLESS;//卡牌的颜色，这里是无色
    // private static final CardColor COLOR = EXAMPLE_GREEN;//使用自定义的颜色
    private static final CardRarity RARITY = CardRarity.SPECIAL;//卡牌的稀有度，这里是基本稀有度
    //BASIC基础; COMMON普通;  UNCOMMON罕见; RARE稀有; SPECIAL特殊; CURSE诅咒
    private static final CardTarget TARGET = CardTarget.ENEMY;//卡牌的目标，这里是对敌人
    public Reconstruction() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        // super(id, name, img, cost, rawDescription, type, color, rarity, target);
        //
        this.baseMagicNumber = 6; // 基础魔法值
        this.magicNumber = this.baseMagicNumber;
        this.shuffleBackIntoDrawPile = true; // 回到抽牌堆

    }

    // 这些方法怎么写，之后再讨论
    @Override
    public void upgrade() {// 升级卡牌
        if (!this.upgraded) {
                   upgradeName();
                   upgradeMagicNumber(3);
            this.selfRetain = true;// 卡牌保留自身，不会被回收。
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;// 读取本地化的升级描述
            this.initializeDescription();// 刷新卡牌描述，显示升级后的效果。
                }

    }

    /**
     * 当卡牌被使用时，调用这个方法。
     *
     * @param p 你的玩家实体类。
     * @param m 指向的怪物类。（无指向时为null，包括攻击所有敌人时）
     */
    public void use(AbstractPlayer p, AbstractMonster m) {
        //addToBot指的是放入执行队列底部。现在的写法，执行顺序是先叠加
        // 抽一张牌
        addToBot((AbstractGameAction)new DrawCardAction(p, 1));
        // 所有敌人失去标记点数生命，数量等于其印记数量的
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDeadOrEscaped()) {
                MarkPower markPower = (MarkPower) monster.getPower(MarkPower.POWER_ID);
                if (markPower != null) {
                    addToBot(new LoseHPAction(
                            monster,
                            p,
                            markPower.amount,
                            AbstractGameAction.AttackEffect.FIRE
                    ));
                }
            }
        }
        // 造成印记效果
        if (m != null) {
            addToTop((AbstractGameAction)new VFXAction((AbstractGameEffect)new PressurePointEffect(m.hb.cX, m.hb.cY)));
        }
        //叠加印记点数
        addToTop((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)p, (AbstractPower)new MarkPower((AbstractCreature)m, this.magicNumber), this.magicNumber));

        //addToBot((AbstractGameAction)new TriggerMarksAction(this));// 触发 MarkPower 的效果
        // 触发 MarkPower 的效果，所有敌人失去标记点数生命
        //addToBot((AbstractGameAction)new LoseHPAction(m, null, 数量, AbstractGameAction.AttackEffect.FIRE));
    }


}