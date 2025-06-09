package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.HashMap;
import java.util.Map;

/**
 *   "LOLWeaponsMod:HaiKeSiKeJiQiangRen": {
 *     "NAME": "海克斯科技枪刃",
 *     "FLAVOR": "魔法与钢铁的婚约，诞下毁灭的子嗣。",
 *     "DESCRIPTIONS": [
 *       "技能牌与攻击牌可以视作彼此"
 *     ]
 *   },
 */
public class HaiKeSiKeJiQiangRen extends CustomRelic {
    public static final String ID = ModHelper.makePath("HaiKeSiKeJiQiangRen");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/HaiKeSiKeJiQiangRen.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/HaiKeSiKeJiQiangRen.png";
    private static final RelicTier RELIC_TIER = RelicTier.SHOP;//类型:BOSS
    //所有类型:COMMON,UNCOMMON,RARE,SPECIAL,BOSS,SHOP,DEPRECATED,STARTER
    private static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;//声音:魔法
    public HaiKeSiKeJiQiangRen() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new HaiKeSiKeJiQiangRen();
    }
    // 遗物效果，可在此处添加代码 TODO
    //攻击牌与技能牌可以视作彼此
    //实现思路：为所有的攻击牌添加技能牌type，为所有的技能牌添加攻击牌标签，以达既是攻击牌又是技能牌的效果。


    // 用于记录每张牌是否已经被转换过
    private Map<AbstractCard, Boolean> convertedCards = new HashMap<>();
    @Override
    public void onCardDraw(AbstractCard card) {//抽牌时触发
        // 检查这张牌是否已经被转换过
        if (!convertedCards.containsKey(card)) {
            if (card.type == AbstractCard.CardType.ATTACK) {
                card.type = AbstractCard.CardType.SKILL;
                card.tags.add(AbstractCard.CardTags.STARTER_STRIKE);
            } else if (card.type == AbstractCard.CardType.SKILL) {
                card.type = AbstractCard.CardType.ATTACK;
                card.tags.add(AbstractCard.CardTags.STARTER_DEFEND);
            }
            // 标记这张牌已经被转换过
            convertedCards.put(card, true);
        }
    }
//    @Override
//    public void onCardDraw(AbstractCard card) {//抽牌时触发
//        if (card.type == AbstractCard.CardType.ATTACK) {
//            card.type=AbstractCard.CardType.SKILL;
//            card.tags.add(AbstractCard.CardTags.STARTER_STRIKE);
//        } else if (card.type == AbstractCard.CardType.SKILL) {
//            card.type=AbstractCard.CardType.ATTACK;
//            card.tags.add(AbstractCard.CardTags.STARTER_DEFEND);
//        }
//    }







//    public void onCardDraw(AbstractCard card) {
//        if (card.type == AbstractCard.CardType.ATTACK) {
//            card.tags.add(AbstractCard.CardTags.STARTER_STRIKE);
//        } else if (card.type == AbstractCard.CardType.SKILL) {
//            card.tags.add(AbstractCard.CardTags.STARTER_DEFEND);
//        }
//    }
//    public void onCardUse(AbstractCard card) {
//        if (card.type == AbstractCard.CardType.ATTACK) {
//            card.tags.add(AbstractCard.CardTags.STARTER_STRIKE);
//        } else if (card.type == AbstractCard.CardType.SKILL) {
//            card.tags.add(AbstractCard.CardTags.STARTER_DEFEND);
//        }
//    }



}
