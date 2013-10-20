package com.flexpoker.core.actionon;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;

import com.flexpoker.config.Command;
import com.flexpoker.core.api.actionon.CreateAndStartActionOnTimerCommand;
import com.flexpoker.core.api.handaction.CallHandActionCommand;
import com.flexpoker.core.api.handaction.FoldHandActionCommand;
import com.flexpoker.model.Game;
import com.flexpoker.model.Seat;
import com.flexpoker.model.Table;

@Command
public class CreateAndStartActionOnTimerImplCommand implements
        CreateAndStartActionOnTimerCommand {

    private static final Logger LOG = Logger.getLogger(CreateAndStartActionOnTimerImplCommand.class);
    
    private final CallHandActionCommand callHandActionCommand;

    private final FoldHandActionCommand foldHandActionCommand;

    @Inject
    @Lazy
    public CreateAndStartActionOnTimerImplCommand(
            CallHandActionCommand callHandActionCommand,
            FoldHandActionCommand foldHandActionCommand) {
        this.callHandActionCommand = callHandActionCommand;
        this.foldHandActionCommand = foldHandActionCommand;
    }

    @Override
    public Timer execute(final Game game, final Table table, final Seat seat) {
        LOG.debug("Creating timer for seat: " + seat);
        LOG.debug("Table: " + table);
        
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                LOG.debug("Time has expired for: " + seat);
                LOG.debug("Table: " + table);

                if (seat.getCallAmount() == 0) {
                    LOG.debug("Automatically calling");
                    callHandActionCommand.execute(game.getId(), table.getId(),
                            seat.getUserGameStatus().getUser());
                } else {
                    LOG.debug("Automatically folding");
                    foldHandActionCommand.execute(game.getId(), table.getId(),
                            seat.getUserGameStatus().getUser());
                }
                timer.cancel();
            }
        }, 10000);

        return timer;
    }

}