package java_bootcamp;

import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ContainerState implements ContractState {


    private int width;
    private int height;
    private int depth;

    private String contents;

    private Party owner;

    private Party carrier;

//////////////////////////

    public ContainerState(int width, int height, int depth, String contents, Party owner, Party carrier) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.contents = contents;
        this.owner = owner;
        this.carrier = carrier;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Party getOwner() {
        return owner;
    }

    public void setOwner(Party owner) {
        this.owner = owner;
    }

    public Party getCarrier() {
        return carrier;
    }

    public void setCarrier(Party carrier) {
        this.carrier = carrier;
    }

    //////////////////////////////7


    public static void main(String []args){

        Party jetpackImporters= null;
        Party jetpackCarriers=null;

        ContainerState container=new ContainerState(2,4,2,"Jetpacks",jetpackImporters,jetpackCarriers);

    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of(owner, carrier);
    }
}
