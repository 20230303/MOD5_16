package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower.DESCRIPTIONS;

/**
 *  "LOLWeaponsMod:EHuXiaoFu": {
 *     "NAME": "恶火小斧",
 *     "FLAVOR": "地狱的余烬在斧刃上低语，渴望着新鲜血肉。",
 *     "DESCRIPTIONS": [""]
 *   },
 */
public class EHuXiaoFu extends CustomRelic
{
    public static final String ID = ModHelper.makePath("EHuXiaoFu");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/EHuXiaoFu.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/EHuXiaoFu.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.UNCOMMON;//类型:BOSS
    //所有类型:COMMON,UNCOMMON,RARE,SPECIAL,BOSS,SHOP,DEPRECATED,STARTER
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.HEAVY;//声音:魔法
    public EHuXiaoFu() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new EHuXiaoFu();//修改这里 TODO
    }
    // 遗物效果，可在此处添加代码 TODO
    // 造成伤害时，减少1点伤害，改为施加1层中毒
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            AbstractPlayer p = AbstractDungeon.player;

            // 选择一个随机存活的敌人
            AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(
                    null, // 不排除任何敌人
                    true, // 只选择存活的敌人
                    AbstractDungeon.cardRandomRng // 使用游戏的随机数生成器
            );

            if (randomMonster != null) {
                // 触发遗物特效
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(p, this));

                // 对随机敌人施加中毒效果
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(
                                randomMonster,
                                p,
                                new PoisonPower(randomMonster, p, 1),
                                1
                        )
                );
            }
        }
    }


//    @Override
//    public void onUseCard(AbstractCard card, UseCardAction action) {
//        if (card.type == AbstractCard.CardType.ATTACK) {
//            AbstractPlayer p = AbstractDungeon.player;
//            AbstractMonster m = null;
//            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
//                if (!mo.isDeadOrEscaped()) {
//                    m = mo;
//                    break;
//                }
//            }
//            if (m != null) {
//                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(p, this));
//                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, 1), 1));
//            }
//        }
//    }

}
