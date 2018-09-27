package java_bootcamp;

import net.corda.core.contracts.Command;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.Party;
import net.corda.core.transactions.LedgerTransaction;

import java.security.PublicKey;
import java.util.List;

public class HouseContract implements Contract {


    @Override
    public void verify(LedgerTransaction tx) throws IllegalArgumentException {

    if(tx.getCommands().size()!=1)
        throw new IllegalArgumentException("Transaction must have one command.");


        Command command= tx.getCommand(0);
        List<PublicKey> requiredSigners= command.getSigners();
        CommandData commandType=command.getValue();

        if(commandType instanceof Register){

            //"Shape" constraints

            if(tx.getInputStates().size()!=0)
                throw new IllegalArgumentException("Registration transaction must have no inputs.");

            if(tx.getOutputStates().size()!=1)
                throw new IllegalArgumentException("Registration transaction must have 1 output.");


            //Content constraints

            ContractState outputState= tx.getOutput(0);

            if(!(outputState instanceof HouseState))
                throw new IllegalArgumentException("Output must be a HouseState.");

            HouseState houseState= (HouseState) outputState;

            if(houseState.getAddress().length() <= 3)
                throw new IllegalArgumentException("Address must be longer than 3 characters.");


            //Required signer constraints

            Party owner= houseState.getOwner();
            PublicKey ownersKey= owner.getOwningKey();

            if(!(requiredSigners.contains(ownersKey)))
                throw new IllegalArgumentException("Owner of house must sign registration.");

        }

        else if(commandType instanceof Transfer){

            ////See part 12 video minute 17:49

            //"Shape" constraints


        }

        else{
            throw new IllegalArgumentException("Command Type not recognised.");

        }


    }

    public static class Register implements CommandData{}

    public static class Transfer implements CommandData{}
}
