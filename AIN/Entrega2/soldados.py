import json
from loguru import logger
from spade.behaviour import OneShotBehaviour
from spade.template import Template
from spade.message import Message
from pygomas.bditroop import BDITroop
from pygomas.bdifieldop import BDIFieldOp
from agentspeak import Actions
from agentspeak import grounded
from agentspeak.stdlib import actions as asp_action
from pygomas.ontology import HEALTH
import random
from pygomas.ontology import DESTINATION

from pygomas.agent import LONG_RECEIVE_WAIT

class BDITropa(BDIFieldOp, BDITroop):

        def add_custom_actions(self, actions):
            super().add_custom_actions(actions)

        
            @actions.add_function(".distance", (tuple,tuple, ))
            def _distance(p1, p2):
                return ((p1[0]-p2[0])**2+(p1[2]-p2[2])**2)**0.5

            @actions.add_function(".distMedia", (tuple,tuple, ))
            def _distMedia(p1, p2):
                return ((p1[0] + p2[0])/2, 0, (p1[2]+ p2[2])/2)

            @actions.add_function(".flagTaken", ())
            def _flagTaken():
                '''
                    @rerturn 1 if the flag is taken, else  0
                '''
                return 1 if self.is_objective_carried else 0
                
            @actions.add_function(".soldiers",())
            def _soldiers():
                '''
                    Returns the number of alive soldiers
                '''
                return self.soldiers_count

            @actions.add_function(".canGO", (tuple, ))
            def _canGO(position):
                '''
                    Looks if the agent can got to the position or not, 
                    very grateful when you need to know if the wall are there
                '''
                
                X, Y, Z = position
                return 1 if self.map.can_walk(X, Z) else 0
            
            @actions.add_function(".tryGO", (tuple, tuple, ))
            def _tryGO(position, posEnemy):
                '''
                    Looks if we can move to the position, if yes, we move there, 
                    if not, we just move to the enemy position
                '''
                X, Y, Z = position
                if self.map.can_walk(X,Z):
                    print("We can go !")
                    return position
                else:
                    return posEnemy
        
            @actions.add_function(".focusedAT", ())
            def _focusedAT():
                '''
                    Returns the agent ID of the agent or None
                ''' 
                return self.aimed_agent

            @actions.add_function(".next", (tuple,tuple, ))
            def _next(pos, flag):
                '''
                    Gives the next possible point to go randomly
                '''
                (px, py, pz) = pos
                (fx, fy, fz) = flag

                def dist(p1, p2):
                    return ((p1[0]-p2[0])**2+(p1[1]-p2[1])**2)**0.5

                p1 = px + random.randint(-100,100)
                p2 = pz + random.randint(-100,100)
                while(not self.map.can_walk(p1, p2) or (dist((p1, p2),(fx, fz)) > 70) or (dist((p1, p2),(fx, fz)) < 20)):
                    p1 = px + random.randint(-100,100)
                    p2 = pz + random.randint(-100,100)
                print(">",p1 ,",",p2, "DIST: ", dist((p1, p2),(fx, fz)))
                return (p1, 0, p2)
            


