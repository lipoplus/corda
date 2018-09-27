package java_bootcamp;

import net.corda.core.contracts.Command;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.Party;
import net.corda.core.transactions.LedgerTransaction;

import java.security.PublicKey;
import java.util.List;

import static net.corda.core.contracts.ContractsDSL.requireThat;

/* Our contract, governing how our state will evolve over time.
 * See src/main/kotlin/examples/ExampleContract.java for an example. */
public class TokenContract implements Contract{
    public static String ID = "java_bootcamp.TokenContract";

    @Override
    public void verify(LedgerTransaction tx) throws IllegalArgumentException {
        if(tx.getCommands().size()!=1) {
            throw new IllegalArgumentException("Transaction must have one command.");
        }
        Command command=tx.getCommand(0);
        CommandData commandType= command.getValue();

        List<PublicKey> requiredSigners= command.getSigners();

        if(commandType instanceof Issue){
            /////////"Shape" constraints
            if(tx.getInputStates().size()!=0){
                throw new IllegalArgumentException("Registration transaction must have no inputs.");
            }

            if(tx.getOutputStates().size()!=1) {
                throw new IllegalArgumentException("Registration transaction must have 1 output.");
            }
            ///////////////////////////

            /////////"Content" constraints
            ContractState outputState= tx.getOutput(0);
            if(!(outputState instanceof TokenState)) {
                throw new IllegalArgumentException("Output must be a TokenState.");
            }

            TokenState tokenState= (TokenState) outputState;
            if(!(tokenState.getAmount() >0)) {
                throw new IllegalArgumentException("Amount must be positive.");
            }
            ///////////////////////////

            /////////"Required Signer" constraints
            Party issuer= tokenState.getIssuer();
            PublicKey ownersKey= issuer.getOwningKey();
            if(!(requiredSigners.contains(ownersKey))) {
                throw new IllegalArgumentException("Owner of house must sign registration.");
            }
            ///////////////////////////
        }else{
            throw new IllegalArgumentException("Not an Issue Command.");
        }
    }

    public static class Issue implements CommandData {}
}


