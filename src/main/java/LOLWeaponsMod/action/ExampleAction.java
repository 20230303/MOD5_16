package LOLWeaponsMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


/**
 * cation: 示例动作
 * 功能：斩杀目标后，抽一张牌。
 * */

public class ExampleAction extends AbstractGameAction {
    // 伤害信息
    public DamageInfo info;

    public ExampleAction(AbstractMonster target, DamageInfo info) {
        this.target = target;
        this.info = info;
    }

    @Override
    public void update() {
        // 目标受到伤害
        this.target.damage(this.info);
        //如果斩杀了目标，抽一张牌
        if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead
                && !this.target.hasPower("Minion")) {
            this.addToTop(new DrawCardAction(1));//addToTop排在最上面，addToBot排在最下面
            //注意当有多个addToTop的时候，由于写在下面的被排到最上边了，写在上面的会后执行！

        }
        this.isDone = true;
    }

}


