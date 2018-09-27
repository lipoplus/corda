package java_bootcamp;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;

public class VerySimpleFlow extends FlowLogic<Integer> {



    @Suspendable
    public Integer call() throws FlowException {
        return 1;
    }
}
