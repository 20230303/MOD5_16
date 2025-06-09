package LOLWeaponsMod.cards;



import LOLWeaponsMod.action.ExampleAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//这段代码不能编译
//报错，因为没有继承抽象方法，需要重写upgrade()方法和use()方法
//这两个方法代表 升级 卡牌需要调用的方法和 使用 卡牌调用的方法，
//这对于一张卡牌是必须的
public class Strike extends CustomCard {
    //信息写成常量方便管理
    //定义卡牌的ID、名称、图片路径、描述、类型、颜色、稀有度、目标

    //public static final String ID = "LOLWeaponsMod:Strike";
    //还是太懒了，调用方法，只需要写实际ID
    //public static final String ID= ModHelper.makePath("Strike");
    // ID 实际等于 "LOLWeaponsMod:Strike"
    //再次简化
    //通常，你的卡牌名和类名是一致的，所以直接获取类名即可，此外还可以骗过文本编辑器，让它在复制时直接修改类名，就不用修改ID了。
    public static final String ID = Strike.class.getSimpleName();
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    // 从游戏系统读取本地化资源

    //private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/cards/attack/Strike.png";
    private static final int COST = 1;// 卡牌的初始费用
    //特殊的：-2费不显示能量图标（如诅咒卡状态卡等），-1费为X费（旋风斩等）
    //private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    // 读取本地化的描述

    private static final CardType TYPE = CardType.ATTACK;// 卡牌的类型，这里是攻击类型.卡牌类型可以同时存在多个吗？
    //ATTACK：攻击类型；SKILL：技能类型；POWER：力量类型；STATUS：状态类型；CURSE：诅咒类型；
    private static final CardColor COLOR = CardColor.COLORLESS;//卡牌的颜色，这里是无色
    // private static final CardColor COLOR = EXAMPLE_GREEN;//使用自定义的颜色
    private static final CardRarity RARITY = CardRarity.BASIC;//卡牌的稀有度，这里是基本稀有度
    //BASIC基础; COMMON普通;  UNCOMMON罕见; RARE稀有; SPECIAL特殊; CURSE诅咒
    private static final CardTarget TARGET = CardTarget.ENEMY;//卡牌的目标，这里是对敌人
    public Strike() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        // super(id, name, img, cost, rawDescription, type, color, rarity, target);
        //
        this.damage = this.baseDamage = 9;
        this.tags.add(CardTags.STARTER_STRIKE);// 基础打击卡牌的标签，用于潘多拉的变化
        this.tags.add(CardTags.STRIKE);// 打击卡牌的标签，用于完美打击计算
        /**
         * baseDamage是卡牌的基础伤害数值，也就是没有计算易伤等之前的伤害。
         * tags是卡牌的标签，
         * 例如添加STARTER_STRIKE（基础打击）让潘多拉变化这张牌，添加STRIKE（打击）让完美打击计算这张牌。
         * 注意添加了STARTER_STRIKE并不会视为添加了STRIKE。
         */


    }

    // 这些方法怎么写，之后再讨论
    @Override
    public void upgrade() {// 升级卡牌
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeDamage(5); // 将该卡牌的伤害提高5点。
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            // 用于描述升级后的效果
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
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {// 使用卡牌
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(
                        //DamageAction(AbstractCreature target, DamageInfo info)
                        //target：伤害目标
                        //info：伤害信息
                        m,
                        new DamageInfo(
                                //DamageInfo(AbstractCreature source, int base, DamageType type)
                                //source：伤害来源
                                //base：伤害值
                                //type：伤害类型
                                p,
                                damage,
                                DamageInfo.DamageType.NORMAL// 伤害类型
                                //攻击伤害使用NORMAL，非攻击伤害（荆棘等）使用THORNS，失去生命使用HP_LOSS。

                        )
                )
        );

        this.addToBot(new ExampleAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
    }

}
/**
 * ID
 * ID是一张卡牌的同种类标识，用来区分不同卡名的卡牌。例如：
 *
 * 打击和防御的ID不同。
 * 打击和打击+的ID相同。
 * 一张痛击和另一张痛击的ID相同。
 * 不同颜色的打击的ID是不同的。（例如蓝色打击为"Strike_B"，红色打击为"Strike_R"）
 * 为了和其他mod区分开来，你需要在ID之前加上你的modid前缀。
 *     public static final String ID = "LOLWeaponsMod:Strike";
 */
