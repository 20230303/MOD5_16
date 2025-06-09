package LOLWeaponsMod.powers;

import LOLWeaponsMod.helpers.ModHelper;
import LOLWeaponsMod.modcore.LOLWeaponsMod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

/**
 *  "LOLWeaponsMod:GangPower": {
 *     "NAME": "Gang",
 *     "DESCRIPTIONS": [
 *       "被攻击时移除1层。",
 *       "当目标有此效果时，玩家攻击获得1点生命上限。"
 *     ]
 *   }
 */
public class GangPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("GangPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;



    public GangPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;
        this.amount = Amount;

        String path128 = "PEACH_LOLWeaponsModResources/img/powers/Gang128.png";
        String path48 = "PEACH_LOLWeaponsModResources/img/powers/Gang48.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();//// 首次添加能力更新描述
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
    // 被攻击时
    public int onAttacked(DamageInfo info, int damageAmount) {
        // 非荆棘伤害，非生命流失伤害，伤害来源不为空，伤害来源不是能力持有者本身，伤害大于0
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && damageAmount > 0) {
            // 能力闪烁一下
            this.flash();
            // 添加1点生命上限action
            AbstractDungeon.player.increaseMaxHp(1, true);//主角获得1点生命上限
            // 减少1层能力
            //addToTop(new ReducePowerAction(this.owner, this.owner, "GangPower", 1));
            // 移除1层能力
            //this.amount--;
            // 检查能力层数是否为0
            //if (this.amount <= 0) {
                //addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "GangPower"));
            //}
        }
        return damageAmount;
    }
//    public void onRemove(){// 移除能力时,什么都不做
//        super.onRemove();
//    }

//
//    @Override
//    public int onAttacked(DamageInfo info, int damageAmount) {
//        if (!pendingRemove &&
//                info.type != DamageInfo.DamageType.THORNS &&
//                info.type != DamageInfo.DamageType.HP_LOSS &&
//                damageAmount > 0 &&
//                this.amount > 0) {
//
//            this.amount--;
//            if (this.amount <= 0) {
//                pendingRemove = true;
//                scheduleRemove();
//            }
//            this.flash();
//        }
//        return damageAmount;
//    }
//
//    // 安排移除能力的Action
//    private void scheduleRemove() {
//        if (owner != null && !owner.isDeadOrEscaped()) {
//            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
//                @Override
//                public void update() {
//                    if (owner != null && owner.powers.contains(GangPower.this)) {
//                        owner.powers.remove(GangPower.this);
//                    }
//                    isDone = true;
//                }
//            });
//        }
//    }
//
//    // 修正：使用正确的回调方法
//
//    public void onAttack(DamageInfo info, DamageInfo.DamageType type) {
//        if (!pendingRemove &&
//                info.owner == AbstractDungeon.player &&
//                this.owner != null &&
//                this.amount > 0) {
//
//            // 玩家获得1点生命上限
//            AbstractDungeon.player.increaseMaxHp(1, true);
//            this.flash();
//        }
//    }
//
//    @Override
//    public void stackPower(int stackAmount) {
//        if (!pendingRemove) {
//            this.amount += stackAmount;
//            this.updateDescription();
//        }
//    }
}